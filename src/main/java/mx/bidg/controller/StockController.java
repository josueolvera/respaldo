package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@PropertySource(value = {"classpath:application.properties"})
public class StockController {

    @Autowired
    private Environment env;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDocumentsService stockDocumentsService;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private StockEmployeeAssignmentsService assignmentsService;

    @Autowired
    private DwEmployeesService dwEmployeesService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll(@RequestParam(name = "idDistributor", required = false) Integer idDistributor) throws IOException {
        List<Stocks> stock;
        if (idDistributor != null) {
            stock = stockService.findByDistributor(idDistributor);
        } else {
            stock = stockService.findAll();
        }
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(
            value = "/{idStock}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<String> updateStock(@PathVariable int idStock, @RequestBody String data) throws IOException {
        JsonNode jnode = mapper.readTree(data);
        Employees employee = new Employees(jnode.get("employee").get("idEmployee").asInt());

        Stocks stock = stockService.findSimpleById(idStock);
        DwEmployees dwEmployee = dwEmployeesService.findBy(
                employee,
                new DwEnterprises(stock.getIdDwEnterprises())
        );

        StockEmployeeAssignments assignment = assignmentsService.getAssignmentFor(stock);

        stock.setSerialNumber(jnode.get("serialNumber").asText());
        stock.setStockFolio(jnode.get("stockFolio").asText());
        stock.setArticleStatus(new CArticleStatus(jnode.get("articleStatus").get("idArticleStatus").asInt()));
        stock.setPurchasePrice(new BigDecimal(jnode.get("purchasePrice").asDouble()));

        if (! assignment.getIdEmmployee().equals(employee.getIdEmployee())) {
            if (dwEmployee == null) {
                throw new ValidationException(
                        "No existe DwEmployees: No se permite resignación de área",
                        "No se permite resignación de área",
                        HttpStatus.FORBIDDEN
                );
            }

            assignment.setCurrentAssignment(0);
            StockEmployeeAssignments newAssignment = new StockEmployeeAssignments();
            newAssignment.setStocks(stock);
            newAssignment.setDwEnterprises(stock.getDwEnterprises());
            newAssignment.setEmployee(employee);
            newAssignment.setAssignmentDate(LocalDateTime.now());
            newAssignment.setCurrentAssignment(1);
            newAssignment.setIdAccessLevel(1);

            assignmentsService.update(assignment);
            assignmentsService.saveAssignment(newAssignment);
        }
        stockService.update(stock);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    @RequestMapping(value = "/{idStock}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDistributor(@PathVariable int idStock) throws IOException {
        Stocks stock = stockService.findById(idStock);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/properties", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getProperties(@PathVariable int idStock) throws IOException {
        List<Properties> properties = propertiesService.getAllFor(new Stocks(idStock));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(properties),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/properties", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<String> saveProperty(@PathVariable int idStock, @RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        CArticles article = new CArticles(node.get("attributesArticles").get("idArticle").asInt());
        CValues value = mapper.treeToValue(node.get("value"), CValues.class);
        CAttributes attribute = mapper.treeToValue(node.get("attributesArticles").get("attributes"), CAttributes.class);

        Properties property = new Properties();
        property.setStocks(new Stocks(idStock));
        property.setValue(value);

        propertiesService.save(property, article, attribute);

        return new ResponseEntity<>("Registro almacenado con exito", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/properties/{idProperty}", method = RequestMethod.DELETE,
            consumes = "application/json", produces = "text/plain;charset=UTF-8"
    )
    public ResponseEntity<String> deleteProperty(@PathVariable int idProperty) {
        propertiesService.delete(new Properties(idProperty));
        return new ResponseEntity<>("Registro eliminado con exito", HttpStatus.OK);
    }

    @RequestMapping(value = "/attachments/download/{idStockDocument}", method = RequestMethod.GET)
    public void readFile(@PathVariable int idStockDocument, HttpServletResponse response) throws IOException {
        String SAVE_PATH = env.getRequiredProperty("stock.documents_dir");
        StockDocuments document = stockDocumentsService.findById(idStockDocument);
        File file = new File(SAVE_PATH + document.getDocumentUrl());

        if (! file.canRead()) {
            throw new ValidationException(
                    "El archivo "+ SAVE_PATH + document.getDocumentUrl() +" no existe",
                    "El documento solicitado no existe"
            );
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

    @RequestMapping(value = "{idStock}/attachments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAttachedDocuments(@PathVariable int idStock) throws IOException {
        List<StockDocuments> documents = stockDocumentsService.findByIdStock(idStock);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documents),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "{idStock}/attachments", method = RequestMethod.POST)
    public ResponseEntity<String> attachDocuments(@PathVariable int idStock, HttpServletRequest request) throws Exception {
        String SAVE_PATH = env.getRequiredProperty("stock.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("stock.attachments.media_types").split(",");
        List<StockDocuments> documents = stockDocumentsService.findByIdStock(idStock);
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

            StockDocuments document = new StockDocuments();
            document.setStock(new Stocks(idStock));
            document.setCStockDocumentsTypes(new CStockDocumentsTypes(idDocumentType));
            document.setDocumentUrl(destFile);
            document.setDocumentName(filePart.getSubmittedFileName());
            document.setUploadingDate(LocalDateTime.now());
            document.setCurrentDocument(1);
            stockDocumentsService.save(document);

            StockDocuments oldDocument = this.findDocument(idDocumentType, documents);
            if (oldDocument != null) {
                oldDocument.setCurrentDocument(0);
                stockDocumentsService.update(oldDocument);
            }
        }

        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    @RequestMapping(value = "{idStock}/attachments/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAttachedDocumentsRecord(@PathVariable int idStock) throws IOException {
        List<StockDocuments> documents = stockDocumentsService.findRecordBy(new Stocks(idStock));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documents),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/assignments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAssignments(@PathVariable int idStock) throws IOException {
        StockEmployeeAssignments assignment = assignmentsService.getAssignmentFor(new Stocks(idStock));
        List<StockEmployeeAssignments> assignments = new ArrayList<>();
        assignments.add(assignment);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(assignments),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/assignments/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAssignmentsRecord(@PathVariable int idStock) throws IOException {
        List<StockEmployeeAssignments> assignments = assignmentsService.getAssignmentsRecordFor(new Stocks(idStock));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(assignments),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/assignments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveAssignment(@PathVariable int idStock, @RequestBody String data) throws IOException {
        JsonNode jnode = mapper.readTree(data);
        DwEnterprises dwEnterprises = new DwEnterprises(jnode.get("idDwEnterprise").asInt());
        Stocks stock = stockService.findSimpleById(idStock);
        StockEmployeeAssignments assignment = assignmentsService.getAssignmentFor(stock);
        StockEmployeeAssignments newAssignment = new StockEmployeeAssignments();

        stock.setDwEnterprises(dwEnterprises);
        assignment.setCurrentAssignment(0);
        newAssignment.setStocks(stock);
        newAssignment.setDwEnterprises(stock.getDwEnterprises());
        newAssignment.setEmployee(assignment.getEmployee());
        newAssignment.setAssignmentDate(LocalDateTime.now());
        newAssignment.setCurrentAssignment(1);
        newAssignment.setIdAccessLevel(1);

        assignmentsService.update(assignment);
        assignmentsService.saveAssignment(newAssignment);
        stockService.update(stock);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(""),
                HttpStatus.CREATED
        );
    }

    @RequestMapping(value = "/{idStock}/assignments", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteAssignment(@PathVariable int idStock) throws IOException {
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(""),
                HttpStatus.CREATED
        );
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
