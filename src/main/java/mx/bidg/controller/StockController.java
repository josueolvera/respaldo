package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.InvalidFileException;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.PropertiesService;
import mx.bidg.service.StockDocumentsService;
import mx.bidg.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDateTime;
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

    @RequestMapping(value = "/{idStock}/properties", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getProperties(@PathVariable int idStock) throws IOException {
        List<Properties> properties = propertiesService.getAllFor(new Stocks(idStock));
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(properties),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idStock}/properties", method = RequestMethod.POST,
            consumes = "application/json", produces = "text/plain;charset=UTF-8"
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
            throw new IOException("El archivo "+ SAVE_PATH + document.getDocumentUrl() +" no existe");
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
                throw new InvalidFileException("Tipo de archivo no admitido");
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
                document.setUploadingDate(LocalDateTime.now());
                stockDocumentsService.save(document);
            } else {
                document.setStock(new Stocks(idStock));
                document.setCStockDocumentsTypes(new CStockDocumentsTypes(idDocumentType));
                document.setDocumentUrl(destFile);
                document.setDocumentName(filePart.getSubmittedFileName());
                document.setUploadingDate(LocalDateTime.now());
                stockDocumentsService.update(document);
            }
        }

        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
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
