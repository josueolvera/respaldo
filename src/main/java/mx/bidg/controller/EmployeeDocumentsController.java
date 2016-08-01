package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.EmployeeDocuments;
import mx.bidg.service.EmployeeDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by gerardo8 on 28/07/16.
 */
@Controller
@RequestMapping("/employee-documents")
public class EmployeeDocumentsController {

    @Autowired
    private EmployeeDocumentsService employeeDocumentsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/{idEmployee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByIdEmployee(
            @PathVariable Integer idEmployee
    ) throws Exception {

        List<EmployeeDocuments> employeeDocuments = employeeDocumentsService.findByIdEmployee(idEmployee);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(employeeDocuments),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/download/{idEmployeeDocument}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void download(
            @PathVariable Integer idEmployeeDocument,
            HttpServletResponse response
    ) throws Exception {

        String SAVE_PATH = env.getRequiredProperty("employees.documents_dir");

        EmployeeDocuments employeeDocument = employeeDocumentsService.findById(idEmployeeDocument);

        File file = new File(SAVE_PATH + employeeDocument.getDocumentUrl());

        if (! file.canRead()) {
            throw new ValidationException(
                    "El archivo "+ SAVE_PATH + employeeDocument.getDocumentUrl() +" no existe",
                    "El documento solicitado no existe"
            );
        }

        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + employeeDocument.getDocumentName() + "\"");
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

    @RequestMapping(value = "/update/{idEmployee}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(
            @PathVariable Integer idEmployee,
            @RequestBody String data
    ) throws Exception {

        EmployeeDocuments employeeDocument = employeeDocumentsService.updateDocument(data,idEmployee);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(employeeDocument),
                HttpStatus.OK
        );
    }
}
