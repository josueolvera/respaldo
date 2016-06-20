/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CBranchs;
import mx.bidg.service.CBranchsService;
import mx.bidg.service.DwEnterprisesService;
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
    private CBranchsService cBranchsService;

    @Autowired
    private DwEnterprisesService dwEnterprisesService;
    
    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findAll() throws Exception {
        List<CBranchs> list = cBranchsService.findAll();
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }

    @RequestMapping(value = "/distributor/{idDistributor}/region/{idRegion}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String save(
            @RequestBody CBranchs branch,
            @PathVariable Integer idDistributor,
            @PathVariable Integer idRegion
    ) throws Exception {
        branch = cBranchsService.save(branch, idDistributor, idRegion);
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(branch);
    }
    @RequestMapping(value = "/{idBranch}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String findById(@PathVariable Integer idBranch) throws Exception {
        CBranchs cBranchs = cBranchsService.findById(idBranch);
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(cBranchs);
    }

    @RequestMapping(value = "/{idBranch}", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String delete(@PathVariable Integer idBranch) throws Exception {
        return map.writerWithView(JsonViews.Root.class).writeValueAsString(cBranchsService.delete(idBranch));
    }
}
