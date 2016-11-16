/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpSession;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Users;
import mx.bidg.service.CommissionEmcofinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Kevin Salvador
 */
@Controller
@RequestMapping("/comisiones")
public class CommissionEmcofinController {

    @Autowired
    private CommissionEmcofinService commissionEmcofinService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String findCommission() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(commissionEmcofinService.findAll());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveExcelCommission(@RequestParam("file") MultipartFile file, @RequestParam("calculateDate") String calculateDate, HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionEmcofinService.saveFromExcel(file, calculateDate, user)), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/check-existing-commission", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> checkExistingOutsourcingRecord(@RequestParam("file") MultipartFile file, @RequestParam("calculateDate") String calculateDate) throws Exception {
        return ResponseEntity.ok(commissionEmcofinService.existsCommissionRecord(file, calculateDate));
    }

    @RequestMapping(value = "/update-excel", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<String> updateExcelOutsourcing(@RequestParam("file") MultipartFile file, @RequestParam("calculateDate") String calculateDate, HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionEmcofinService.updateFromExcel(file, calculateDate, user)), HttpStatus.OK
        );
    }

}
