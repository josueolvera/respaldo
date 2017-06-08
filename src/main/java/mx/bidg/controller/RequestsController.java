package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.events.requests.RequestCompletedEvent;
import mx.bidg.events.requests.RequestCreatedEvent;
import mx.bidg.model.*;
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
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class)
                        .writeValueAsString(requests),HttpStatus.OK
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
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(request), HttpStatus.OK);
    }

}
