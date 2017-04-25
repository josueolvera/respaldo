package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CSubareas;
import mx.bidg.model.Users;
import mx.bidg.service.CSubareasService;
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
 * Created by jcesar on 25/04/2017.
 */
@Controller
@RequestMapping("/subareas")
public class CSubareasController {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    CSubareasService cSubareasService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CSubareas> subareas = cSubareasService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(subareas), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-subareas", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException{
        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        CSubareas cSubareas = new CSubareas();
        cSubareas.setSubareaName(node.get("name").asText());
        cSubareas.setAcronym(node.get("acronym").asText());
        cSubareas.setCreationDate(LocalDateTime.now());
        cSubareas.setUsername(user.getUsername());

        cSubareasService.save(cSubareas);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cSubareasService.findAll())
                ,HttpStatus.OK);
    }
}
