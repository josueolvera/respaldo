package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBussinessLine;
import mx.bidg.model.Users;
import mx.bidg.service.CBussinessLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Kevin Salvador on 21/03/2017.
 */
@Controller
@RequestMapping("bussiness-line")
public class CBussinessLineController {

    @Autowired
    private CBussinessLineService cBussinessLineService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<String> findAll() throws Exception {
        List<CBussinessLine> businessLineList = cBussinessLineService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(businessLineList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save (@RequestBody String data, HttpSession session) throws Exception{
        Users users = (Users) session.getAttribute("user");

        JsonNode node = mapper.readTree(data);

        CBussinessLine bussinessLine = new CBussinessLine();
        bussinessLine.setName(node.get("name").asText());
        bussinessLine.setAcronym(node.get("acronym").asText());
        bussinessLine.setStatus(true);
        bussinessLine.setCreationDate(LocalDateTime.now());
        bussinessLine.setUserName(users.getUsername());

        cBussinessLineService.save(bussinessLine);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cBussinessLineService.findAll()), HttpStatus.OK);
    }
}
