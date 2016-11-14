package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpSession;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Users;
import mx.bidg.service.OutsourcingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by gerardo8 on 17/05/16.
 */
@Controller
@RequestMapping("/outsourcing")
public class OutsourcingController {

    @Autowired
    private OutsourcingService outsourcingService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String findOutsourcing() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(outsourcingService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findOutsourcingById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(outsourcingService.findById(id));
    }

    @RequestMapping(value = "/excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> saveExcelOutsourcing(@RequestParam("file") MultipartFile file,@RequestParam("calculateDate") String calculateDate, HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(outsourcingService.saveFromExcel(file,calculateDate,user)), HttpStatus.OK
        );
    }
    @RequestMapping(value = "/update-excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> updateExcelOutsourcing(@RequestParam("file") MultipartFile file,@RequestParam("calculateDate") String calculateDate,HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(outsourcingService.updateFromExcel(file,calculateDate,user)), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/check-existing-outsourcing",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<Boolean> checkExistingOutsourcingRecord(@RequestParam("file") MultipartFile file ,@RequestParam("calculateDate") String calculateDate) throws Exception {
        return ResponseEntity.ok(outsourcingService.existsOutsourcingRecord(file,calculateDate));
    }
}
