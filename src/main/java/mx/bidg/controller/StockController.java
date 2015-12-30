package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CStockDocumentsTypes;
import mx.bidg.model.StockDocuments;
import mx.bidg.model.Stocks;
import mx.bidg.service.StockDocumentsService;
import mx.bidg.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@Controller
@RequestMapping("stock")
public class StockController {

//    private final String SAVE_PATH = "/run/media/Centos/Storage/SIAD/stock";
    private final String SAVE_PATH = "/tmp/stock";

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDocumentsService stockDocumentsService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> findAll() throws IOException {
        List<Stocks> stock = stockService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idDistributor}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> findByDistributor(@PathVariable int idDistributor) throws IOException {
        List<Stocks> stock = stockService.findByDistributor(idDistributor);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/attachments/download/{idStockDocument}", method = RequestMethod.GET)
    public void readFile(@PathVariable int idStockDocument, HttpServletResponse response) throws IOException {
        StockDocuments document = stockDocumentsService.findById(idStockDocument);
        File file = new File(SAVE_PATH + document.getDocumentUrl());

        if (! file.canRead()) {
            throw new IOException("El archivo "+ SAVE_PATH + document.getDocumentName() +" no existe");
        }

        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getDocumentName() + "\"");
        response.setContentLengthLong(file.length());

        byte[] buffer = new  byte[1024];
        int len;

        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @RequestMapping(value = "/attachments/{idStock}", method = RequestMethod.POST)
    public String attachDocuments(@PathVariable int idStock, HttpServletRequest request) throws Exception {
        List<StockDocuments> documents = stockDocumentsService.findByIdStock(idStock);
        Pattern pattern = Pattern.compile("(\\d+)");

        for (Part filePart: request.getParts()) {
            if (! filePart.getName().matches("^file-type-[0-9]+$")) {
                continue;
            }
            if (filePart.getSize() <= 0) {
                continue;
            }

            Matcher matcher = pattern.matcher(filePart.getName());
            matcher.find();
            Integer idDocumentType = Integer.parseInt(matcher.group(0));

            String destDir = "/article_" + idStock;
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

            StockDocuments document = this.findDocument(idDocumentType, documents);

            if (document == null) {
                document = new StockDocuments();
                document.setStock(new Stocks(idStock));
                document.setCStockDocumentsTypes(new CStockDocumentsTypes(idDocumentType));
                document.setDocumentUrl(destFile);
                document.setDocumentName(filePart.getSubmittedFileName());
                stockDocumentsService.save(document);
            } else {
                document.setStock(new Stocks(idStock));
                document.setCStockDocumentsTypes(new CStockDocumentsTypes(idDocumentType));
                document.setDocumentUrl(destFile);
                document.setDocumentName(filePart.getSubmittedFileName());
                stockDocumentsService.update(document);
            }
        }

        return "redirect:" + request.getHeader("Referer");
    }

    private StockDocuments findDocument(Integer idDocumentType, List<StockDocuments> documents) {
        if (documents.isEmpty()) {
            return null;
        }
        for (StockDocuments document : documents) {
            if (document.getIdDocumentType().equals(idDocumentType)) {
                return document;
            }
        }

        return null;
    }
}
