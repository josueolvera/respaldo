/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.CBranchsService;
import mx.bidg.service.DwEnterprisesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    
    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findAll() throws Exception {
        List<CBranchs> list = cBranchsService.findAll();
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }

    @RequestMapping(
            value = "/distributor/{idDistributor}/region/{idRegion}/zona/{idZona}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public @ResponseBody String save(
            @RequestBody CBranchs branch,
            @PathVariable Integer idDistributor,
            @PathVariable Integer idRegion,
            @PathVariable Integer idZona
    ) throws Exception {
        branch = cBranchsService.save(branch, idDistributor, idRegion, idZona);
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(branch);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String update(@RequestBody String data) throws Exception {
        JsonNode node = mapper.readTree(data);

        int idBranch = node.get("idBranch").asInt();
        int idDistributor = node.get("idDistributor").asInt();
        int idRegion = node.get("idRegion").asInt();
        int idZona = node.get("idZona").asInt();
        int idDwEnterprise = node.get("idDwEnterprise").asInt();
        String branchName = node.get("branchName").asText();
        int flag = node.get("edit").asInt();

        CBranchs cBranchs = cBranchsService.findById(idBranch);

        if(flag == 1){
            cBranchs.setBranchName(branchName.toUpperCase());
            String clearBranchName = StringUtils.stripAccents(branchName);
            String branchNameClean= clearBranchName.replaceAll("\\W", "").toUpperCase();
            cBranchs.setBranchNameClean(branchNameClean);
            cBranchs = cBranchsService.update(cBranchs);
            DwEnterprises currentDwEnterprises =
                    dwEnterprisesService.findById(idDwEnterprise);

            CBranchs oldBranch = currentDwEnterprises.getBranch();
            CDistributors oldDistributor = currentDwEnterprises.getDistributor();
            CRegions oldRegion = currentDwEnterprises.getRegion();
            CZonas oldZona = currentDwEnterprises.getZona();
            CGroups oldGroup = currentDwEnterprises.getGroup();
            CAreas oldArea = currentDwEnterprises.getArea();
            CBussinessLine oldBussinessLine = currentDwEnterprises.getBusinessLine();
            Integer oldBudgetable = currentDwEnterprises.getBudgetable();

            currentDwEnterprises.setDistributor(new CDistributors(idDistributor));
            currentDwEnterprises.setRegion(new CRegions(idRegion));
            currentDwEnterprises.setZona(new CZonas(idZona));
            dwEnterprisesService.update(currentDwEnterprises);

            DwEnterprises dwEnterprises = new DwEnterprises();
            dwEnterprises.setBussinessLine(oldBussinessLine);
            dwEnterprises.setBranch(oldBranch);
            dwEnterprises.setDistributor(oldDistributor);
            dwEnterprises.setRegion(oldRegion);
            dwEnterprises.setZona(oldZona);
            dwEnterprises.setGroup(oldGroup);
            dwEnterprises.setArea(oldArea);
            dwEnterprises.setBudgetable(oldBudgetable);
            dwEnterprises.setStatus(false);
            dwEnterprisesService.save(dwEnterprises);
        }else {
            cBranchs.setBranchName(branchName.toUpperCase());
            String clearBranchName = StringUtils.stripAccents(branchName);
            String branchNameClean= clearBranchName.replaceAll("\\W", "").toUpperCase();
            cBranchs.setBranchNameClean(branchNameClean);
            cBranchs = cBranchsService.update(cBranchs);
        }
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cBranchs);
    }

    @RequestMapping(value = "/change-branch-status", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String changeBranchStatus(@RequestBody Integer idBranch, HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        CBranchs branch = cBranchsService.changeBranchStatus(idBranch, user);
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(branch);
    }

    @RequestMapping(value = "/{idBranch}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String findById(@PathVariable Integer idBranch) throws Exception {
        CBranchs cBranchs = cBranchsService.findById(idBranch);
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cBranchs);
    }

    @RequestMapping(value = "/{idBranch}", method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private @ResponseBody String delete(@PathVariable Integer idBranch) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cBranchsService.delete(idBranch));
    }

    @RequestMapping(value = "/create-report", method = RequestMethod.GET)
    public ResponseEntity<String> createReport(@RequestParam(required = true, name = "file_name") String fileName,
                                               HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".xls\"");

        OutputStream outputStream = response.getOutputStream();
        cBranchsService.branchDistributorsReport(outputStream);
        outputStream.flush();
        outputStream.close();
        return new ResponseEntity<>("Reporte", HttpStatus.OK);
    }
}
