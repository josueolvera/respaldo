/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.dao.DistributorsDetailBanksDao;
import mx.bidg.model.CDistributors;
import mx.bidg.model.Users;
import mx.bidg.service.CDistributorsService;
import mx.bidg.service.DistributorsDetailBanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/distributors")
public class CDistributorsController {

    @Autowired
    private CDistributorsService cDistributorsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DistributorsDetailBanksService distributorsDetailBanksService;

    @RequestMapping(value = "/find-budget",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>findAll()throws Exception{
        List<CDistributors> cDistributorsList = cDistributorsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cDistributorsList),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getCDistributors(
            @RequestParam(name = "forStock", required = false) Boolean forStock,
            @RequestParam(name = "forBudget", required = false) Boolean forBudget,
            @RequestParam(name = "forAgreement", required = false) Boolean forAgreement
    ) throws Exception {
        List<CDistributors> distributors = cDistributorsService.getDistributors(forStock, forBudget, forAgreement);
        for(CDistributors distributor : distributors){
            BigDecimal amount = distributorsDetailBanksService.sumByDistributor(distributor.getIdDistributor());
            distributor.setAccountBalance(amount);
        }
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(distributors);
    }

    @RequestMapping(value = "/agreement", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDistributorsByAgreement() throws IOException{
        List<CDistributors> cDistributors = cDistributorsService.findAllForAgreement();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cDistributors), HttpStatus.OK);
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDistributorByStock() throws IOException{
        List<CDistributors> cDistributorses = cDistributorsService.findAllForStock();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cDistributorses), HttpStatus.OK);
    }

    @RequestMapping(value= "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session) throws IOException{

        Users users = (Users) session.getAttribute("user");

        JsonNode node = mapper.readTree(data);

        CDistributors distributors = new CDistributors();
        distributors.setDistributorName(node.get("name").asText());
        distributors.setAcronyms(node.get("acronyms").asText());
        distributors.setHasStock(true);
        distributors.setCreationDate(LocalDateTime.now());

        if (users != null) {
            distributors.setUsername(users.getUsername());
        }

        distributors.setSaemFlag(false);
        distributors.setHasAgreement(false);
        distributors.setBudgetShare(0);

        cDistributorsService.save(distributors);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cDistributorsService.getDistributors(null, null, null)), HttpStatus.OK);
    }
}
