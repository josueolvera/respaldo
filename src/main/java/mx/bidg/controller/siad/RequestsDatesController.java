package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;

import mx.bidg.model.CRequestStatus;
import mx.bidg.model.Requests;
import mx.bidg.model.RequestsDates;
import mx.bidg.model.Users;
import mx.bidg.service.CRequestStatusService;
import mx.bidg.service.RequestsDatesService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    RequestsService requestsService;

    @Autowired
    CRequestStatusService cRequestStatusService;

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

    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByRequest(@PathVariable Integer idRequest) throws IOException{
        RequestsDates requestsDates = requestsDatesService.getByRequest(idRequest);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsDates), HttpStatus.OK);
    }

    @RequestMapping(value = "/reschedule/{idRequest}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> reschedulePD(@PathVariable Integer idRequest, @RequestBody String data) throws IOException{

        JsonNode node = mapper.readTree(data);

        LocalDateTime scheduledDate = (node.get("applicationDate") == null || node.findValue("applicationDate").asText().equals("")) ? null :
                LocalDateTime.parse(node.get("applicationDate").asText(), DateTimeFormatter.ISO_DATE_TIME);

        RequestsDates requestsDates = requestsDatesService.getByRequest(idRequest);

        requestsDates.setScheduledDate(scheduledDate);

        requestsDates = requestsDatesService.update(requestsDates);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsDates),HttpStatus.OK);
    }

    @RequestMapping(value = "/save-requestdates", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException {
        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        LocalDateTime datePay = (node.get("payLimit") == null || node.findValue("datePay").asText().equals("")) ? null :
                LocalDateTime.parse(node.get("datePay").asText(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime scheDates = (node.get("scheDate") == null || node.findValue("scheDate").asText().equals("")) ? null :
                LocalDateTime.parse(node.get("scheDate").asText(), DateTimeFormatter.ISO_DATE_TIME);

        RequestsDates requestsDates = new RequestsDates();
        CRequestStatus cRequestStatus = cRequestStatusService.findById(8);
        Requests requests = requestsService.findById(node.get("requestId").asInt());

        requestsDates.setRequests(requests);
        requestsDates.setCountUpdate(1);
        requestsDates.setScheduledDate(scheDates);
        requestsDates.setCreationDate(LocalDateTime.now());
        requestsDates.setUserName(user.getUsername());
        requests.setRequestStatus(cRequestStatus);

        requests = requestsService.update(requests);
        requestsDates = requestsDatesService.save(requestsDates);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).
                writeValueAsString(requestsDates), HttpStatus.OK);
    }

    @RequestMapping(value = "/update-rdates", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@RequestBody String data, HttpSession session)throws IOException {
        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        LocalDateTime scheDates = (node.get("scheDate") == null || node.findValue("scheDate").asText().equals("")) ? null :
                LocalDateTime.parse(node.get("scheDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        RequestsDates updateRD = requestsDatesService.getByRequest(node.get("requestId").asInt());
        int addCount = (node.get("countUpdate").asInt());

        updateRD.setCountUpdate(addCount + 1);
        updateRD.setScheduledDate(scheDates);
        updateRD.setUserName(user.getUsername());
        updateRD.setCreationDate(LocalDateTime.now());
        updateRD = requestsDatesService.update(updateRD);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).
                writeValueAsString(updateRD), HttpStatus.OK);
    }

}
