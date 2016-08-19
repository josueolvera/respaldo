package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.model.DitributorsEmployeeDocuments;
import mx.bidg.model.DwEnterprises;
import mx.bidg.service.CEmployeeDocumentsTypesService;
import mx.bidg.service.DistributorsEmployeeDocumentsService;
import mx.bidg.service.DwEnterprisesService;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by jolvera on 29/06/16.
 */
@Controller
@RequestMapping("employee-document-types")
public class CEmployeeDocumentsTypesController {

    @Autowired
    CEmployeeDocumentsTypesService cEmployeeDocumentsTypesService;

    @Autowired
    DwEnterprisesService dwEnterprisesService;

    @Autowired
    DistributorsEmployeeDocumentsService distributorsEmployeeDocumentsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws Exception{
        List<CEmployeeDocumentsTypes> documentsTypes = cEmployeeDocumentsTypesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documentsTypes), HttpStatus.OK);
    }

    @RequestMapping(value = "/employee/{idDwEmployee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByEmployee(@PathVariable Integer idDwEmployee) throws Exception{
        List<CEmployeeDocumentsTypes> documentsTypes = cEmployeeDocumentsTypesService.findByEmployee(idDwEmployee);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documentsTypes), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idBranch}/{idArea}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByBranchAndArea(@PathVariable Integer idBranch, @PathVariable Integer idArea) throws IOException{
        DwEnterprises dwEnterprise = dwEnterprisesService.findByBranchAndArea(idBranch,idArea);
        List<DitributorsEmployeeDocuments> distributorsDocuments = distributorsEmployeeDocumentsService.findByDistributor(dwEnterprise.getIdDistributor());
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDocuments), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<String> findByDistributor (@PathVariable Integer idDistributor) throws IOException{
        List<DitributorsEmployeeDocuments> documentsList = distributorsEmployeeDocumentsService.findByDistributor(idDistributor);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(documentsList), HttpStatus.OK);
    }
}
