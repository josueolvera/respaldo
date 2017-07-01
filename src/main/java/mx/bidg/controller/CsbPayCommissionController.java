package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CsbPayCommission;
import mx.bidg.model.Users;
import mx.bidg.service.CsbPayCommissionService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Serch on 29/06/2017.
 */
@Controller
@RequestMapping("csb-pay-commission")
public class CsbPayCommissionController {

    @Autowired
    private CsbPayCommissionService csbPayCommissionService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CsbPayCommission> csbPayCommissionList =  csbPayCommissionService.findAll();

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(csbPayCommissionList), HttpStatus.OK);
    }

    @RequestMapping(value = "/check-existing-sale",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<Boolean> checkExistingSale(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(csbPayCommissionService.existsSales(file));
    }

    @RequestMapping(value = "/excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> saveExcelSapSale(@RequestParam("file") MultipartFile file, HttpSession session) throws IllegalStateException, IOException, InvalidFormatException {

        Users user = (Users) session.getAttribute("user");
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(csbPayCommissionService.saveFromExcel(file, user)), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/update-excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> updateExcelSapSale(@RequestParam("file") MultipartFile file, HttpSession session) throws Exception {

        Users user = (Users) session.getAttribute("user");

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(csbPayCommissionService.updateFromExcel(file, user)), HttpStatus.OK
        );
    }
}
