package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.service.DwBranchsService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by gerardo8 on 16/05/16.
 */
@Controller
@RequestMapping("/dw-branchs")
public class DwBranchsController {

    @Autowired
    private DwBranchsService dwBranchsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String findDwBranchs() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(dwBranchsService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findDwBranchsById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(dwBranchsService.findById(id));
    }

    @RequestMapping(value = "/update-excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> updateExcelDwBranchs(@RequestParam("file") MultipartFile file) throws Exception {

        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwBranchsService.updateFromExcel(file)), HttpStatus.OK
        );
    }
}
