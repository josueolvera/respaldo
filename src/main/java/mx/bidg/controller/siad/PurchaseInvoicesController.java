package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.PurchaseInvoices;
import mx.bidg.service.PurchaseInvoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * Created by g_on_ on 17/06/2017.
 */

@Controller
@RequestMapping("/purchase-invoices")
public class PurchaseInvoicesController {

    @Autowired
    private PurchaseInvoicesService purchaseInvoicesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByRequest(@PathVariable Integer idRequest) throws IOException{
        PurchaseInvoices purchaseInvoices = purchaseInvoicesService.findByRequest(idRequest);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(purchaseInvoices), HttpStatus.OK);
    }

}
