/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.pojos.HierarchicalLevel;
import mx.bidg.service.DwEnterprisesService;
import mx.bidg.service.EmployeesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/dw-enterprises")
public class DwEnterprisesController {
    
    @Autowired
    private DwEnterprisesService dwEnterprisesService;

    @Autowired
    private EmployeesHistoryService employeesHistoryService;
    
    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getDwEnterprises(
            @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
            @RequestParam(name = "idRegion", required = false) Integer idRegion,
            @RequestParam(name = "idZona", required = false) Integer idZona,
            @RequestParam(name = "idBranch", required = false) Integer idBranch,
            @RequestParam(name = "idArea", required = false) Integer idArea
    ) throws Exception {
        List<DwEnterprises> dwEnterprises = dwEnterprisesService.findByDistributorRegionZonaBranchAndArea(idDistributor, idRegion, idZona, idBranch, idArea);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEnterprises), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{idDwEnterprise}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getById(@PathVariable int idDwEnterprise) throws Exception {
        DwEnterprises dwEnterprises = dwEnterprisesService.findById(idDwEnterprise);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEnterprises), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{idGroup}/{idArea}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByGroupArea(@PathVariable int idGroup, @PathVariable int idArea) throws Exception {
        List<DwEnterprises> list = dwEnterprisesService.findByGroupArea(new CGroups(idGroup), new CAreas(idArea));
        return new ResponseEntity<>(map.writerWithView(JsonViews.IdsEnterprises.class).writeValueAsString(list), HttpStatus.OK);
    }

    @RequestMapping(value = "distributor/{idDistributor}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getByDistrubutor(@PathVariable int idDistributor) throws Exception {
        List<DwEnterprises> list = dwEnterprisesService.findByDistributor(idDistributor);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK);
    }

    @RequestMapping(value = "distributor/{idDistributor}/area/{idArea}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> getByDistrubutorAndArea(@PathVariable int idDistributor, @PathVariable int idArea) throws Exception {
        List<DwEnterprises> list = dwEnterprisesService.findByDistributorAndArea(idDistributor, idArea);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/{idUser}", produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getByIdUser(@PathVariable int idUser) throws JsonProcessingException {
        DwEnterprises dw = dwEnterprisesService.findByIdUser(idUser);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Root.class).writeValueAsString(dw), HttpStatus.OK);
    }

    @RequestMapping(value = "/hierarchy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getHierarchy() throws IOException {
        List<HierarchicalLevel> hierarchy = dwEnterprisesService.findHierarchicalStructure();
        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(hierarchy),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/distributor-region-branch/{idDistributor}/{idRegion}/{idBranch}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDRB(@PathVariable Integer idDistributor,@PathVariable Integer idRegion, @PathVariable Integer idBranch)throws IOException{
        DwEnterprises dwEnterprise = dwEnterprisesService.findByDistributorRegionBranch(idDistributor,idRegion,idBranch);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEnterprise),HttpStatus.OK);
    }

    @RequestMapping(value = "/branch/{idBranch}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBranch(@PathVariable Integer idBranch) throws IOException {
        List<DwEnterprises> dwEnterprises = dwEnterprisesService.findByBranches(idBranch);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEnterprises), HttpStatus.OK);
    }

    @RequestMapping(value = "/branch-area/{idBranch}/{idArea}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDwEnterprisebyBranchAndArea(@PathVariable Integer idBranch,@PathVariable Integer idArea) throws IOException{
        DwEnterprises dwEnterprises = dwEnterprisesService.findByBranchAndArea(idBranch,idArea);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEnterprises), HttpStatus.OK);
    }

    @RequestMapping(value = "/employee-history/{idEH}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getByEH(@PathVariable Integer idEH) throws IOException{
        EmployeesHistory employeesHistory = employeesHistoryService.findById(idEH);
        DwEnterprises dwEnterprises = dwEnterprisesService.findById(employeesHistory.getIdDwEnterprise());
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEnterprises), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor-area/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAreaByDistributor(@PathVariable Integer idDistributor) throws IOException{
        List<CAreas> areasList = dwEnterprisesService.findAreaByDistributor(idDistributor);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(areasList), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor-region/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getRegionByDistributor(@PathVariable Integer idDistributor) throws IOException{
        List<CRegions> regionsList = dwEnterprisesService.findRegionByDistributor(idDistributor);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(regionsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor-region-zona/{idDistributor}/{idRegion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getZonaByDistributorAndRegion(@PathVariable Integer idDistributor, @PathVariable Integer idRegion) throws IOException{
        List<CZonas> zonasList = dwEnterprisesService.findZonaByDistributorAndRegion(idDistributor, idRegion);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(zonasList), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor-region-zona-branch/{idDistributor}/{idRegion}/{idZonas}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBranchByDistributorAndRegionAndZona(@PathVariable Integer idDistributor, @PathVariable Integer idRegion, @PathVariable Integer idZonas) throws IOException{
        List<CBranchs> branchsList = dwEnterprisesService.findBranchByDistributorAndRegionAndZona(idDistributor, idRegion, idZonas);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(branchsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/branch-area/{idBranch}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAreasByBranch(@PathVariable Integer idBranch) throws IOException{
        List<CAreas> areasList = dwEnterprisesService.findAreaByBranch(idBranch);
        return new ResponseEntity<>(map.writerWithView(JsonViews.Embedded.class).writeValueAsString(areasList), HttpStatus.OK);
    }
}
