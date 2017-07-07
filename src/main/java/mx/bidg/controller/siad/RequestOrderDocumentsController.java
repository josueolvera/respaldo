package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.reports.PurchaseOrderReportService;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jcesar on 24/05/2017.
 */

@Controller
@RequestMapping("/order-documents-request")
public class RequestOrderDocumentsController {
    @Autowired
    private RequestOrderDocumentsService requestOrderDocumentsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RequestOrderDetailService requestOrderDetailService;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private PriceEstimationsService priceEstimationsService;

    @Autowired
    private ProvidersService providersService;

    @Autowired
    private CDistributorsService cDistributorsService;

    @Autowired
    private PurchaseOrderReportService purchaseOrderReportService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<RequestOrderDocuments> accountingAccounts = requestOrderDocumentsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString
                (accountingAccounts), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-order-documents",
            method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session) throws IOException {

        JsonNode node = mapper.readTree(data);

        Users user = (Users) session.getAttribute("user");

        Requests request = requestsService.findById(node.get("idRequest").asInt());

        RequestOrderDocuments requestOrderDocuments = new RequestOrderDocuments();

        if (request != null){

            requestOrderDocuments.setRequest(request);

            Providers provider = providersService.findById(node.get("provider").get("idProvider").asInt());
            if (provider != null){
                requestOrderDocuments.setProvider(provider);
            }

            PriceEstimations priceEstimations = priceEstimationsService.findAuthorized(request.getIdRequest());
            if (priceEstimations != null){
                requestOrderDocuments.setPriceEstimations(priceEstimations);
            }

            CDistributors distributor = cDistributorsService.findById(node.get("distributor").get("idDistributor").asInt());
            if (distributor != null){
                requestOrderDocuments.setcDistributor(distributor);
            }

            requestOrderDocuments.setContactNameTransmitter(node.get("contactName").asText());
            requestOrderDocuments.setContactTelTransmitter(node.get("contactCel").asText());
            requestOrderDocuments.setContactRoleTransmitter(node.get("contactRole").asText());
            requestOrderDocuments.setAmount(new BigDecimal(node.get("amount").asDouble()));
            requestOrderDocuments.setIva(new BigDecimal(node.get("iva").asDouble()));
            requestOrderDocuments.setTotalAmount(new BigDecimal(node.get("totalAmount").asDouble()));
            requestOrderDocuments.setCreationDate(LocalDateTime.now());
            requestOrderDocuments.setUsername(user.getUsername());


            requestOrderDocuments = requestOrderDocumentsService.save(requestOrderDocuments);

            for (JsonNode jNode : node.get("orderProducts")){
                RequestOrderDetail requestOrderDetail = new RequestOrderDetail();
                requestOrderDetail.setRequestOrderDocuments(requestOrderDocuments);
                requestOrderDetail.setUsername(user.getUsername());
                requestOrderDetail.setCreationDate(LocalDateTime.now());
                requestOrderDetail.setDescription(jNode.get("description").asText());
                requestOrderDetail.setQuantity(jNode.get("quantity").asInt());
                requestOrderDetail.setPrice(new BigDecimal(jNode.get("price").asText()));

                requestOrderDetailService.save(requestOrderDetail);
            }
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString
                (requestOrderDocuments), HttpStatus.OK);
    }

    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByIdRequest(@PathVariable Integer idRequest)throws Exception{
        RequestOrderDocuments requestOrderDocument = requestOrderDocumentsService.findByIdRequest(idRequest);

        if (requestOrderDocument != null){
            List<RequestOrderDetail> requestOrderDetails = requestOrderDetailService.findByidReqOrderDocument(requestOrderDocument.getIdRequestOrderDocument());

            if (!requestOrderDetails.isEmpty()){
                requestOrderDocument.setRequestOrderDetails(requestOrderDetails);
            }
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestOrderDocument), HttpStatus.OK);
    }

    @RequestMapping(value="/{idRequestOrderDocument}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer idRequestOrderDocument)throws Exception{

        RequestOrderDocuments requestOrderDocuments = requestOrderDocumentsService.findById(idRequestOrderDocument);
        boolean status = requestOrderDocumentsService.delete(requestOrderDocuments);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(status), HttpStatus.OK);
    }

    @RequestMapping(value = "/purchase-order-report", method = RequestMethod.GET, produces = "application/pdf")
    public @ResponseBody byte[] getReport() throws ParseException {

        //PurchaseOrderReportService prueba = new PurchaseOrderReportService();

        return purchaseOrderReportService.getReportPurchaseOrder();

    }
}
