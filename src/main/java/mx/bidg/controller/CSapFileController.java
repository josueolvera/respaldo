package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CSapFile;
import mx.bidg.service.CSapFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 12/05/16.
 */
@Controller
@RequestMapping("sap-file")
public class CSapFileController {

    @Autowired
    private CSapFileService cSapFileService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<CSapFile> cSapFiles = cSapFileService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cSapFiles), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idSapFile}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,@PathVariable int idSapFile) throws IOException {
        CSapFile cSapFile = cSapFileService.update(file,idSapFile);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cSapFile), HttpStatus.OK);
    }
}
