package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CProductsRequest;
import mx.bidg.service.CProductsRequestService;
import mx.bidg.service.RoleProductRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.model.RoleProductRequest;

/**
 * Created by g_on_ on 23/05/2017.
 */
@Controller
@RequestMapping("role-product-request")
public class RoleProductRequestController {

    @Autowired
    private RoleProductRequestService roleProductRequestService;

    @Autowired
    private CProductsRequestService productsRequestService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/product/{idCostCenter}/{idAccountingAccounts}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findIdsProductsRequestByDistributorCostCenter(@PathVariable Integer idCostCenter,@PathVariable Integer idAccountingAccounts) throws IOException {
        List<RoleProductRequest> productsRequests =  roleProductRequestService.getProductsRequestByCostCenterAndAA(idCostCenter, idAccountingAccounts);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(productsRequests), HttpStatus.OK);
    }
}
