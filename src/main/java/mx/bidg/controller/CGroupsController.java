/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.servlet.http.HttpSession;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CGroups;
import mx.bidg.model.Users;
import mx.bidg.service.CGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/groups")
public class CGroupsController {
    
    @Autowired
    CGroupsService cGroupsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getCGroups() 
            throws Exception {
        List<CGroups> list = cGroupsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{idGroup}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getCGroupsParams(
        @PathVariable Integer idGroup) throws Exception {        
        CGroups cGroup = cGroupsService.getByIdBudgetsCatalogs(idGroup);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.EmbeddedBudget.class).writeValueAsString(cGroup), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/area", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getBudgetListByGroupArea(HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        CGroups cGroup = cGroupsService.getBudgetListByGroupsArea(user.getDwEmployee().getDwEnterprise().getIdGroup(), 
                user.getDwEmployee().getDwEnterprise().getIdArea());
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.EmbeddedBudget.class).writeValueAsString(cGroup), HttpStatus.OK);
    }
    
}
