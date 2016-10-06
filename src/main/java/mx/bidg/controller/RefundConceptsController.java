package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.RefundConcepts;
import mx.bidg.pojos.xml_bill.Comprobante;
import mx.bidg.service.RefundConceptsService;
import mx.bidg.utils.XMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gerardo8 on 22/07/16.
 */
@Controller
@RequestMapping("/refund-concepts")
public class RefundConceptsController {

    @Autowired
    private RefundConceptsService refundConceptsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/voucher-xml-data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> voucherXmlData(@RequestBody String data) throws Exception {

        Comprobante comprobante = refundConceptsService.getVoucherXmlData(data);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(comprobante),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idRefund}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(
            @RequestBody String data,
            @PathVariable Integer idRefund
    ) throws Exception {

        RefundConcepts refundConcept = refundConceptsService.save(data, idRefund);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(refundConcept.getIdRefundConcept()),
                HttpStatus.OK
        );
    }
}
