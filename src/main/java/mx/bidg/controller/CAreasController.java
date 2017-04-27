/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAreas;
import mx.bidg.model.CGroups;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Users;
import mx.bidg.service.CAreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/areas")
public class CAreasController {
    
    @Autowired
    CAreasService cAreasService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody String getAreas() throws Exception {
        List<CAreas> list = cAreasService.findAll();
        return mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(list);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cAreasService.findById(id));
    }

    @RequestMapping(value = "/area-role/{idArea}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAreasWithRoles(@PathVariable Integer idArea)throws IOException{
        CAreas areas = cAreasService.findAreaWhitRole(idArea);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(areas),HttpStatus.OK);
    }

    @RequestMapping(value = "/save-areas", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveAreas (@RequestBody String data, HttpSession session) throws IOException{
        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        CAreas area = new CAreas();
        area.setAreaName(node.get("name").asText());
        area.setCreationDate(LocalDateTime.now());
        area.setSaemFlag(0);
        area.setUsername(user.getUsername());
        cAreasService.save(area);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cAreasService.findAll()), HttpStatus.OK);
    }
}
