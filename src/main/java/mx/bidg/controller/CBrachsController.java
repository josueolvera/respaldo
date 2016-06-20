/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBranchs;
import mx.bidg.service.CBranchsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/branchs")
public class CBrachsController {
    
    @Autowired
    CBranchsService cBranchsService;
    
    ObjectMapper map = new ObjectMapper();
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody String getAreas() throws Exception {
        List<CBranchs> list = cBranchsService.findAll();
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
    @RequestMapping(value = "/{idBranch}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String findById(@PathVariable Integer idBranch) throws Exception {
        CBranchs cBranchs = cBranchsService.findById(idBranch);
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(cBranchs);
    }
}
