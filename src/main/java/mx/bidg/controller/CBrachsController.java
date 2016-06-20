/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.CBranchsService;
import mx.bidg.service.DwEnterprisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/update", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String update(@RequestBody String data) throws Exception {
        JsonNode node = map.readTree(data);

        int idBranch = node.get("idBranch").asInt();
        int idDistributor = node.get("idDistributor").asInt();
        int idRegion = node.get("idRegion").asInt();
        int idDwEnterprise = node.get("idDwEnterprise").asInt();

        CBranchs cBranchs = cBranchsService.findById(idBranch);
        DwEnterprises currentDwEnterprises =
                dwEnterprisesService.findById(idDwEnterprise);

        CBranchs oldBranch = currentDwEnterprises.getBranch();
        CDistributors oldDistributor = currentDwEnterprises.getDistributor();
        CRegions oldRegion = currentDwEnterprises.getRegion();
        CGroups oldGroup = currentDwEnterprises.getGroup();
        CAreas oldArea = currentDwEnterprises.getArea();
        Integer oldBudgetable = currentDwEnterprises.getBudgetable();

        currentDwEnterprises.setDistributor(new CDistributors(idDistributor));
        currentDwEnterprises.setRegion(new CRegions(idRegion));
        currentDwEnterprises.setStatus(true);
        dwEnterprisesService.update(currentDwEnterprises);

        DwEnterprises dwEnterprises = new DwEnterprises();
        dwEnterprises.setBranch(oldBranch);
        dwEnterprises.setDistributor(oldDistributor);
        dwEnterprises.setRegion(oldRegion);
        dwEnterprises.setGroup(oldGroup);
        dwEnterprises.setArea(oldArea);
        dwEnterprises.setBudgetable(oldBudgetable);
        dwEnterprises.setStatus(false);
        dwEnterprisesService.save(dwEnterprises);

        return map.writerWithView(JsonViews.Root.class).writeValueAsString(cBranchs);
    }

    @RequestMapping(value = "/change-branch-status", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String changeBranchStatus(@RequestBody Integer idBranch) throws Exception {
        CBranchs branch = cBranchsService.changeBranchStatus(idBranch);
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
