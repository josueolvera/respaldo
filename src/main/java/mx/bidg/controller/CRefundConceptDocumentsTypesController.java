package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRefundConceptDocumentsTypes;
import mx.bidg.service.CRefundConceptDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gerardo8 on 22/07/16.
 */
@RestController
@RequestMapping("/refund-concept-documents-types")
public class CRefundConceptDocumentsTypesController {

    @Autowired
    private CRefundConceptDocumentsTypesService cRefundConceptDocumentsTypesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByIdVoucherType(
            @RequestParam(name = "idVoucherType", required = false) Integer idVoucherType
    ) throws Exception {

        List<CRefundConceptDocumentsTypes> refundConceptDocumentsTypes;

        if (idVoucherType != null) {
            refundConceptDocumentsTypes = cRefundConceptDocumentsTypesService.findByIdVoucherType(
                    idVoucherType
            );
        } else {
            refundConceptDocumentsTypes = cRefundConceptDocumentsTypesService.findAll();
        }



        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(refundConceptDocumentsTypes),
                HttpStatus.OK
        );
    }
}
