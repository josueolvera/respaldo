package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.InvalidFileException;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.TransactionsDocumentsService;
import mx.bidg.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jolvera on 14/04/2016.
 */
@Controller
@RequestMapping("transactions")
public class TransactionsController {

    @Autowired
    Environment env;

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    TransactionsDocumentsService transactionsDocumentsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<Transactions> transactions = transactionsService.findAll();
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(transactions),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/entry-pay-account", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> entryPayAccount(@RequestBody String data,HttpSession session) throws IOException{
        Users user = (Users) session.getAttribute("user");
        transactionsService.entryPayAccount(data,user);
        return new ResponseEntity<>("Transaccion exitosa", HttpStatus.OK);
    }

    @RequestMapping(value = "/report/transactions-by-date", method = RequestMethod.GET)
    public ResponseEntity<String> reportTransaction(@RequestParam(name= "fromDate", required=true) String fromDate, @RequestParam(name="toDate", required=true) String toDate ,HttpServletResponse response) throws IOException {

        LocalDateTime ofDate = (fromDate == null || fromDate.equals("")) ? null :
                LocalDateTime.parse(fromDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (toDate == null || toDate.equals("")) ? null :
                LocalDateTime.parse(toDate, DateTimeFormatter.ISO_DATE_TIME);

        String initialDate = ofDate.toLocalDate().toString();
        String finalDate = untilDate.toLocalDate().toString();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" +"Reporte de transacciones por ingreso de "+initialDate+" a "+finalDate+".xls"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        transactionsService.transacctionsByDayReport(ofDate, untilDate, outputStream);
        outputStream.flush();
        outputStream.close();
        return new ResponseEntity<>("Reporte", HttpStatus.OK);
    }

    @RequestMapping(value = "/report/accounts-payable", method = RequestMethod.GET)
    public ResponseEntity<String> reportPayAccount(@RequestParam(name= "fromDate", required=true) String fromDate, @RequestParam(name="toDate", required=true) String toDate , HttpServletResponse response) throws IOException {

        LocalDateTime ofDate = (fromDate == null || fromDate.equals("")) ? null :
                LocalDateTime.parse(fromDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime untilDate = (toDate == null || toDate.equals("")) ? null :
                LocalDateTime.parse(toDate, DateTimeFormatter.ISO_DATE_TIME);

        String initialDate = ofDate.toLocalDate().toString();
        String finalDate = untilDate.toLocalDate().toString();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" +"Reporte cuentas pagadas de "+initialDate+" a "+finalDate+".xls"+ "\"");
        OutputStream outputStream = response.getOutputStream();
        transactionsService.transacctionsByDayReportAndExit(ofDate, untilDate, outputStream);
        outputStream.flush();
        outputStream.close();
        return new ResponseEntity<>("Reporte", HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTransaction}/attachment",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveDocumentsByTransaction(@PathVariable Integer idTransaction,HttpServletRequest request) throws Exception{

        String SAVE_PATH = env.getRequiredProperty("transactions.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("transactions.attachments.media_types").split(",");

        Pattern pattern = Pattern.compile("(\\d+)");

        for (Part filePart: request.getParts()) {
            boolean isValidMediaType = false;

            if (! filePart.getName().matches("^file-type-[0-9]+$")) {
                continue;
            }
            if (filePart.getSize() <= 0) {
                continue;
            }

            for (String mediaType : fileMediaTypes) {
                if (filePart.getContentType().equals(mediaType)) {
                    isValidMediaType = true;
                    break;
                }
            }

            if (! isValidMediaType) {
                throw new ValidationException("Tipo de archivo no admitido", "Tipo de archivo no admitido");
            }

            Matcher matcher = pattern.matcher(filePart.getName());
            matcher.find();
            Integer idDocumentType = Integer.parseInt(matcher.group(0));

            String destDir = "/transaction_" + idTransaction;
            String destFile = destDir + "/Documento." + idDocumentType +
                    "." + Calendar.getInstance().getTimeInMillis();

            File dir = new File(SAVE_PATH + destDir);
            if (! dir.exists()) {
                dir.mkdir();
            }   
            
            OutputStream outputStream = new FileOutputStream(new File(SAVE_PATH + destFile));
            InputStream inputStream = filePart.getInputStream();
            int read;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            TransactionsDocuments document = new TransactionsDocuments();
            document.setTransaction(new Transactions(idTransaction));
            document.setTransactionDocumentType(new CTransactionsDocumentsTypes(idDocumentType));
            document.setDocumentUrl(destFile);
            document.setDocumentName(filePart.getSubmittedFileName());
            document.setUploadingDate(LocalDateTime.now());
            transactionsDocumentsService.save(document); 
        }
        return new ResponseEntity<>("Archivos guardados con exito",HttpStatus.OK);
    }
}
