package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CSapFile;
import mx.bidg.model.StockDocuments;
import mx.bidg.service.CSapFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by gerardo8 on 12/05/16.
 */
@RestController
@RequestMapping("sap-file")
public class CSapFileController {

    @Autowired
    private CSapFileService cSapFileService;

    @Autowired
    private ObjectMapper mapper;

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

    @RequestMapping(value = "/layout/download/{idSapFile}", method = RequestMethod.GET)
    public void readFile(@PathVariable int idSapFile, HttpServletResponse response) throws IOException {

        CSapFile sapFile = cSapFileService.findById(idSapFile);

        File file = new File(sapFile.getLayoutFileUrl());

        if (! file.canRead()) {
            throw new ValidationException(
                    "El archivo "+ sapFile.getLayoutFileUrl() +" no existe",
                    "El documento solicitado no existe"
            );
        }

        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + sapFile.getLayoutFileName() + "\"");
        response.setContentLengthLong(file.length());

        byte[] buffer = new  byte[1024];
        int len;

        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}
