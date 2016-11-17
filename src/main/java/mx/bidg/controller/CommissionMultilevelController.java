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
import mx.bidg.service.CommissionMultilevelService;
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
@RequestMapping("/multilevel")
public class CommissionMultilevelController {
    @Autowired
    private CommissionMultilevelService commissionMultilevelService;
    
    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String findMultilevel() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(commissionMultilevelService.findAll());
    }
    
    @RequestMapping(value = "/excel", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveExcelMultilevel(@RequestParam("file") MultipartFile file, @RequestParam("calculateDate") String calculateDate, HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionMultilevelService.save(file, calculateDate, user)), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/check-existing-multilevel", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> checkExistingMultilevelRecord(@RequestParam("file") MultipartFile file, @RequestParam("calculateDate") String calculateDate) throws Exception {
        return ResponseEntity.ok(commissionMultilevelService.existsMultilevelRecord(file, calculateDate));
    }

    @RequestMapping(value = "/update-multilevel", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<String> updateExcelMultilevel(@RequestParam("file") MultipartFile file, @RequestParam("calculateDate") String calculateDate, HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(commissionMultilevelService.update(file, calculateDate, user)), HttpStatus.OK
        );
    }
    
}
