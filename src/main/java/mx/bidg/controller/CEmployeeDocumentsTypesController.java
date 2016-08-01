package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.service.CEmployeeDocumentsTypesService;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by jolvera on 29/06/16.
 */
@Controller
@RequestMapping("employee-document-types")
public class CEmployeeDocumentsTypesController {

    @Autowired
    CEmployeeDocumentsTypesService cEmployeeDocumentsTypesService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws Exception{
        List<CEmployeeDocumentsTypes> documentsTypes = cEmployeeDocumentsTypesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documentsTypes), HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/{idEmployee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByEmployee(@PathVariable Integer idEmployee) throws Exception{
        List<CEmployeeDocumentsTypes> documentsTypes = cEmployeeDocumentsTypesService.findByEmployee(idEmployee);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documentsTypes), HttpStatus.OK);
    }
}
