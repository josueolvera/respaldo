package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.RequestOrderDocuments;
import mx.bidg.model.Users;
import mx.bidg.service.RequestOrderDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jcesar on 24/05/2017.
 */

@Controller
@RequestMapping("/order-documents-request")
public class RequestOrderDocumentsController {
    @Autowired
    private RequestOrderDocumentsService requestOrderDocumentsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<RequestOrderDocuments> accountingAccounts = requestOrderDocumentsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString
                (accountingAccounts), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-order-documents",
            method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session) throws IOException {
        int pediente = 10;
        JsonNode node = mapper.readTree(data);
        Users user = (Users) session.getAttribute("user");
        RequestOrderDocuments requestOrderDocuments = new RequestOrderDocuments();
        requestOrderDocuments.setIdPriceEstimation(pediente);
        requestOrderDocuments.setFilePath(node.get("filePath").asText());
        requestOrderDocuments.setFileName(node.get("fileName").asText());
        requestOrderDocuments.setCreationDate(LocalDateTime.now());
        requestOrderDocuments.setUsername(user.getUsername());
        requestOrderDocumentsService.save(requestOrderDocuments);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString
                (requestOrderDocumentsService.findAll()), HttpStatus.OK);
    }
}
