package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.events.requests.RequestCompletedEvent;
import mx.bidg.events.requests.RequestCreatedEvent;
import mx.bidg.model.*;
import mx.bidg.reports.ProofPaymentReportService;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * created on 18/11/15.
 */
@Controller
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private DistributorCostCenterService distributorCostCenterService;

    @Autowired
    private AccountsPayableService accountsPayableService;

    @Autowired
    private RequestHistoryService requestHistoryService;

    @Autowired
    private PeriodicPaymentsService periodicPaymentsService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AccountingAccountsService accountingAccountsService;

    @Autowired
    private PriceEstimationsService priceEstimationsService;

    @Autowired
    private RequestProductsService requestProductsService;

    @Autowired
    private EmployeesHistoryService employeesHistoryService;

    @Autowired
    private DwEmployeesService dwEmployeesService;

    @Autowired
    private PurchaseInvoicesService purchaseInvoicesService;

    @Autowired
    private RequestsDatesService requestsDatesService;

    @Autowired
    private DistributorsDetailBanksService distributorsDetailBanksService;

    @Autowired
    private PayRequestsHistoryService payRequestsHistoryService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProofPaymentReportService proofPaymentReportService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveRequest(@RequestBody String data, HttpSession session)
            throws Exception{

        Users user = (Users) session.getAttribute("user");
        Requests request = requestsService.saveData(data, user);
        String response;

        if(request != null) {
//            eventPublisher.publishEvent(new RequestCreatedEvent(request));
            response = mapper.writeValueAsString(request);

        } else {
            response = "Error al guardar la Solicitud";
        }

        return ResponseEntity.ok(response);

    }


    @RequestMapping(value = "/month-branch-product-type", method = RequestMethod.POST,
            headers = {"Accept=application/json;charset=UTF-8"}, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getBudgetMonthProductType(@RequestBody String data) throws Exception {
        return mapper.writeValueAsString(requestsService.getBudgetMonthProductType(data));
    }


    @RequestMapping(value = "/period-payment", method = RequestMethod.POST, headers = "Accept=application/json; charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String savePeriodicPayment(@RequestBody String data) throws Exception {
        PeriodicsPayments periodicsPayment = periodicPaymentsService.saveData(data);
        eventPublisher.publishEvent(new RequestCompletedEvent(requestsService.findByFolio(periodicsPayment.getFolio())));
        return mapper.writeValueAsString(periodicsPayment);
    }

    @RequestMapping(value="/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findRequestByID(@PathVariable int idRequest) throws Exception {
        Requests request = requestsService.findById(idRequest);
        if (request != null){
            List<PriceEstimations> priceEstimations = priceEstimationsService.findByIdRequest(request.getIdRequest());
            if(!priceEstimations.isEmpty()){
                request.setPriceEstimationsList(priceEstimations);
            }

            List<RequestProducts> requestProducts = requestProductsService.findByIdRequest(request.getIdRequest());
            if (!requestProducts.isEmpty()){
                request.setRequestProductsList(requestProducts);
            }

            EmployeesHistory employeesHistory = employeesHistoryService.findByIdEmployeeAndLastRegister(request.getIdEmployee());

            if (employeesHistory != null){
                DwEmployees dwEmployees = dwEmployeesService.findByIdDw(employeesHistory.getIdDwEmployee());
                if (dwEmployees != null){
                    request.setDwEmployees(dwEmployees);
                }
            }
        }

//        request.getRequestTypeProduct().setAccountingAccountInfo(accountingAccountsService.findById(request.getRequestTypeProduct().getIdAccountingAccount()));
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(request));
    }

    @RequestMapping(value = "/folio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByFolio(@RequestParam(name = "folio", required = true) String folio) throws IOException{
        Requests requests = requestsService.findByFolio(folio);
        return new ResponseEntity<>( mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requests),HttpStatus.OK
        );
    }

    @RequestMapping(value = "/change-active-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeActiveStatus(@RequestParam(name = "request") Integer idRequest) throws IOException{
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class)
                        .writeValueAsString(requestsService.changeActiveStatus(idRequest)),HttpStatus.OK
        );
    }

    @RequestMapping(value = "/status/{idRequestStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByRequestCategory(@PathVariable Integer idRequestCategory) throws IOException{

        List<Requests> requests = requestsService.findByRequestCategory(idRequestCategory);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requests), HttpStatus.OK);
    }

    @RequestMapping(value = "/category/{idRequestCategory}/type/{idRequestType}/employee/{idEmployee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getRequestByCategoryAndType(@PathVariable Integer idRequestCategory, @PathVariable Integer idRequestType, @PathVariable Integer idEmployee) throws IOException{
        List<Requests> requestsList = requestsService.findByCategoryAndTypeByEmployee(idRequestCategory, idRequestType, idEmployee);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/category/{idRequestCategory}/type/{idRequestType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getRequestByCAndT(@PathVariable Integer idRequestCategory, @PathVariable Integer idRequestType) throws IOException{
        List<Requests> requestsList = requestsService.findByCategoryAndType(idRequestCategory, idRequestType);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{idRequest}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteRequest(@PathVariable Integer idRequest)throws IOException{
        boolean requestStatus = requestsService.deleteRequest(idRequest);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/status/category/{idRequestCategory}/type/{idRequestType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getRequestByCAndTAndStatus(@PathVariable Integer idRequestCategory, @PathVariable Integer idRequestType) throws IOException{
        List<Requests> requestsList = requestsService.findByCategoryAndType(idRequestCategory, idRequestType);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> rejectRequest(@RequestBody String data, HttpSession session)throws IOException{

        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        Requests request = requestsService.rejectRequest(node.get("idRequest").asInt(), node.get("rejectJustify").asText(), user);

        EmailTemplates emailTemplates = emailTemplatesService.findByName("reject_requests");
        emailTemplates.addProperty("request", request);
        emailTemplates.addProperty("usuario", user);

        Users userRequest = usersService.findByUserName(request.getUserName());
        emailDeliveryService.deliverEmailWithUser(emailTemplates, userRequest);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(request), HttpStatus.OK);
    }

    @RequestMapping(value = "/send-to-paymanagement", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> sendToPaymanagement(@RequestParam(name = "idRequest") Integer idRequest, HttpSession session)throws IOException{
        Users user = (Users) session.getAttribute("user");

        Requests request = requestsService.findById(idRequest);
        if (request != null){
            request.setRequestStatus(CRequestStatus.EN_PROCESO_DE_PAGO);
            request = requestsService.update(request);

            RequestHistory requestHistory = requestHistoryService.saveRequest(request, user);

            EmailTemplates emailTemplates = emailTemplatesService.findByName("send_payment_notification");
            emailTemplates.addProperty("request", request);
            emailTemplates.addProperty("usuario", user);
            emailDeliveryService.deliverEmail(emailTemplates);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(request), HttpStatus.OK);
    }

    @RequestMapping(value = "/by-status/{idRequestStatus}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByStatus(@PathVariable Integer idRequestStatus)throws IOException{
        List<Requests> requests = requestsService.findByStatus(idRequestStatus);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requests), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<Requests> requestsList = requestsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDistributor(@PathVariable Integer idDistributor) throws IOException{
        List<Requests> requestsList = requestsService.findByDistributor(idDistributor);
        if (!requestsList.isEmpty()){
            for (Requests request : requestsList){
                request.setPurchaseInvoices(purchaseInvoicesService.findByIdRequest(request.getIdRequest()));
                request.setRequestsDates(requestsDatesService.getByRequest(request.getIdRequest()));
            }
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/pay-selected", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> payRequests(@RequestBody String data, HttpSession session) throws IOException {
        JsonNode node = mapper.readTree(data);
        Users user = (Users) session.getAttribute("user");
        List<Requests> requestsList = new ArrayList<>();
        for(JsonNode jsonNode : node.get("requestsSelected")){
            requestsList.add(requestsService.payRequest(jsonNode.get("idRequest").asInt(), user));
            DistributorsDetailBanks distributorsDetailBanks = distributorsDetailBanksService.findById(jsonNode.get("bank").get("idDistributorDetailBank").asInt());
            BigDecimal resta = distributorsDetailBanks.getAmount().subtract(jsonNode.get("purchaseInvoices").get("totalAmount").decimalValue());
            distributorsDetailBanks.setAmount(resta);
            distributorsDetailBanks = distributorsDetailBanksService.update(distributorsDetailBanks);
        }
        proofPaymentReportService.getProofPaymentReport(data);
        List<PayRequestsHistory> payRequestsHistories = payRequestsHistoryService.saveData(data, user);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(payRequestsHistories), HttpStatus.OK);
    }

    @RequestMapping(value = "/proof-payment-report", method = RequestMethod.GET, produces = "application/pdf")
    public @ResponseBody byte[] getProofPaymentReport(@RequestBody String data) throws ParseException {

        //PurchaseOrderReportService prueba = new PurchaseOrderReportService();

        return proofPaymentReportService.getProofPaymentReport(data);

    }

    @RequestMapping(value =  "/folios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findListByFolio(@RequestParam(name = "folio", required = false) String folio) throws IOException{
        List<Requests> requestsList = requestsService.findListByFolio(folio);
        if (!requestsList.isEmpty()){
            for (Requests request : requestsList){
                request.setPurchaseInvoices(purchaseInvoicesService.findByIdRequest(request.getIdRequest()));
                request.setRequestsDates(requestsDatesService.getByRequest(request.getIdRequest()));
            }
        }
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/send-to-buy-management/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> sendToBuyManagement(@PathVariable Integer idRequest, HttpSession session) throws IOException {
        Users user = (Users) session.getAttribute("user");

        Requests request = requestsService.sendToBuyMAnagementRequest(idRequest, user);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(request), HttpStatus.OK);
    }

    @RequestMapping(value = "/authorized-amount-status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findBySatusAndAuthorizedAmount(HttpSession session)throws IOException{
        Users user = (Users) session.getAttribute("user");

        List<Requests> requests = requestsService.findByAuthorizedAmounAndStatus(user);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requests), HttpStatus.OK);
    }

    @RequestMapping(value = "/send-to-financial-planing/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> sendToFinancialPlaning(@PathVariable Integer idRequest, HttpSession session) throws IOException {
        Users user = (Users) session.getAttribute("user");

        Requests request = requestsService.sendToFinancialPlaningRequest(idRequest, user);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(request), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributors-with-requests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllWithStatus8() throws IOException{
        List<CDistributors> distributorsList = requestsService.findRequestNumberByDistributor();

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsList), HttpStatus.OK);
    }
}
