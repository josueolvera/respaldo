package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;

import mx.bidg.model.RequestsDates;
import mx.bidg.model.Users;
import mx.bidg.service.RequestsDatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by jcesar on 12/06/2017.
 */
@Controller
@RequestMapping("/accounts-payables-dates")
public class RequestsDatesController {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    RequestsDatesService requestsDatesService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<RequestsDates> requestsDates = requestsDatesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsDates),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findByIds(@PathVariable int id) throws IOException {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(requestsDatesService.findById(id));
    }

    @RequestMapping(value = "/save-requestDates", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException{
        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        RequestsDates requestsDates = new RequestsDates();
        requestsDates.setIdRequests(node.get("idRequest").asInt());
//        requestsDates.setPaydayLimit(node.get("payLimit").asText());
//        requestsDates.setScheduiedDate(node.get("scheDate").);
        requestsDates.setCountUpdate(node.get("countUp").asInt());
        requestsDates.setCreationDate(LocalDateTime.now());
        requestsDates.setUserName(user.getUsername());
        requestsDatesService.save(requestsDates);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).
                writeValueAsString(requestsDatesService.findAll()), HttpStatus.OK);
    }

}
