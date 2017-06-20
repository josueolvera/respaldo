package mx.bidg.controller;

import mx.bidg.model.CRequestsCategories;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@Controller
@RequestMapping("/siad")
public class SIADController {

    @RequestMapping(value = "/budget", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView budgetsView() {
        ModelAndView model = new ModelAndView();
        model.addObject("now", LocalDateTime.now());
        model.setViewName("Budget");
        return model;
    }

    @RequestMapping(value = "/authorization-budget", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView authorizationBudget(){
        ModelAndView model = new ModelAndView();
        model.addObject("now", LocalDateTime.now());
        model.setViewName("Authorization-Budget");
        return model;
    }

    @RequestMapping(value = "/budget-report", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView individualBudget() {
        ModelAndView model = new ModelAndView();
        model.addObject("now", LocalDateTime.now());
        model.setViewName("BudgetReport");
        return model;
    }


    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView stockView() {
        ModelAndView model = new ModelAndView();
        model.setViewName("Stock");
        return model;
    }

    @RequestMapping(value = "/stock/{idStock}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView singleStockView(@PathVariable Integer idStock) {
        ModelAndView model = new ModelAndView();
        model.addObject("idStock", idStock);
        model.setViewName("single-stock");
        return model;
    }

    @RequestMapping(value = "/directa", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView directaRequestType() {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", 0);
        model.addObject("cat", CRequestsCategories.SOLICITUD.getIdRequestCategory());
        model.setViewName("DirectRequest");
        return model;
    }

    @RequestMapping(value = "/cotizable", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView cotizableRequestTypeSearch() {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", 0);
        model.addObject("cat", CRequestsCategories.PAGO_PROVEEDORES.getIdRequestCategory());
        model.setViewName("Requests");
        return model;
    }

    @RequestMapping(value = "/periodica", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView periodicRequestTypeSearch() {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", 0);
        model.setViewName("PeriodicRequest");
        return model;
    }

    @RequestMapping(value = "/directa/{idRequest}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView directaRequestType(@PathVariable int idRequest) {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.addObject("cat", CRequestsCategories.SOLICITUD.getIdRequestCategory());
        model.setViewName("DirectRequest");
        return model;
    }

    @RequestMapping(value = "/cotizable/{idRequest}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView cotizableRequestTypeSearch(@PathVariable int idRequest) {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.addObject("cat", CRequestsCategories.PAGO_PROVEEDORES.getIdRequestCategory());
        model.setViewName("CotizableRequest");
        return model;
    }

    @RequestMapping(value = "/periodica/{idRequest}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView periodicRequestTypeSearch(@PathVariable int idRequest)
    {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.setViewName("PeriodicRequest");
        return model;
    }

    @RequestMapping(value = "/search-request", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String searchRequest() {
        return "SearchRequest";
    }

    @RequestMapping(value = "/suppliers", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView suppliersView( )
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("Suppliers");
        return model;
    }

    @RequestMapping(value = "/accounts-payables", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView accountsPayablesView( )
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("accounts-payables");
        return model;
    }

    @RequestMapping(value = "/accounts-payable-info/{idRequest}/{idAccountPayable}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView accountsPayablesinfoView(@PathVariable int idRequest, @PathVariable int idAccountPayable)
    {
        ModelAndView model = new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.addObject("idAccountPayable", idAccountPayable);
        model.setViewName("accountspayableinfo");
        return model;
    }

    @RequestMapping(value = "/travel-expenses",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView travelExpensesRequestType(@RequestParam(name = "travel_expense", required = false) Integer idTravelExpence) {
        ModelAndView model = new ModelAndView();
        model.addObject("travel_expense", idTravelExpence);
        model.addObject("cat", CRequestsCategories.VIATICOS);
        model.setViewName("TravelExpenses");
        return model;
    }

    @RequestMapping(value = "/plane-tickets",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView planeTicketsRequestType(@RequestParam(name = "plane_ticket", required = false) Integer idPlaneTicket) {
        ModelAndView model = new ModelAndView();
        model.addObject("plane_ticket", idPlaneTicket);
        model.addObject("cat", CRequestsCategories.BOLETOS_DE_AVION);
        model.setViewName("PlaneTickets");
        return model;
    }

    @RequestMapping(value = "/refunds",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView refundsRequestType(@RequestParam(name = "refund", required = false) Integer idRefund) {
        ModelAndView model = new ModelAndView();
        model.addObject("refund", idRefund);
        model.addObject("cat", CRequestsCategories.REEMBOLSOS);
        model.setViewName("Refunds");
        return model;
    }

    @RequestMapping(value = "/check/{idCheck}",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView checks(@PathVariable Integer idCheck) {
        ModelAndView model = new ModelAndView();
        model.addObject("check", idCheck);
        model.setViewName("Checks");
        return model;
    }

    @RequestMapping(value = "/requests-management",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView requestsManagement() {
        ModelAndView model = new ModelAndView();
        model.setViewName("RequestsManagement");
        return model;
    }

    @RequestMapping(value = "/requests-management/check/{idCheck}",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView authorizeCheck(@PathVariable Integer idCheck) {
        ModelAndView model = new ModelAndView();
        model.addObject("check", idCheck);
        model.setViewName("RequestsManagementCheck");
        return model;
    }

    @RequestMapping(value = "/requests-management/plane-ticket/{idPlaneTicket}",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView authorizePlaneTicket(@PathVariable Integer idPlaneTicket) {
        ModelAndView model = new ModelAndView();
        model.addObject("planeTicket", idPlaneTicket);
        model.setViewName("RequestsManagementPlaneTicket");
        return model;
    }

    @RequestMapping(value = "/requests-management/refund/{idRefund}",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView authorizeRefund(@PathVariable Integer idRefund) {
        ModelAndView model = new ModelAndView();
        model.addObject("refund", idRefund);
        model.setViewName("RequestsManagementRefund");
        return model;
    }

    @RequestMapping(value = "/requests-management/travel-expense/{idTravelExpense}",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView authorizeTravelExpense(@PathVariable Integer idTravelExpense) {
        ModelAndView model = new ModelAndView();
        model.addObject("travelExpense", idTravelExpense);
        model.setViewName("RequestsManagementTravelExpense");
        return model;
    }

    @RequestMapping(value = "/accounting-accounts",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView accountingAccounts() {
        ModelAndView model= new ModelAndView();
        model.setViewName("accountingAccount");
        return model;
    }
    
    @RequestMapping(value = "/cost-allocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView costAllocation(){
        ModelAndView model = new ModelAndView();
        model.setViewName("cost-allocation");
        return model;
    }
    
    @RequestMapping(value = "/cost-client", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView costClient(@RequestParam(name = "idDwEmployee") Integer idDwEmployee) {
        ModelAndView model = new ModelAndView();
        model.addObject("idDwEmployee", idDwEmployee);
        model.setViewName("costClient");
        return model;
    }
    
    @RequestMapping(value = "/report-cost", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadCostReport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("report-cost");
        return model;
    }
    
    @RequestMapping(value = "/budget-automatic",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public ModelAndView budgetAssignable(){
        ModelAndView model = new ModelAndView();
        model.setViewName("BudgetAutomatic");
        return model;
    }
    
    @RequestMapping(value = "/c-bussines-line", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView bussinesLineView() {
        ModelAndView model = new ModelAndView();
        model.setViewName("c-bussines-line");
        return model;
    }

    @RequestMapping(value = "/request-spending-detail-current", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadRequestSpendingDetailCurrent(@RequestParam(name = "idRequest") Integer idRequest) {
        ModelAndView model = new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.setViewName("request-spending-detail-current");
        return model;
    }

    @RequestMapping(value = "/buy", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadBuy() {
        ModelAndView model = new ModelAndView();
        model.setViewName("buy");
        return model;
    }

    @RequestMapping(value = "/c-distributors", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadDistributors() {
        ModelAndView model = new ModelAndView();
        model.setViewName("c-distributors");
        return model;
    }

    @RequestMapping(value = "/c-regions", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadRegions() {
        ModelAndView model = new ModelAndView();
        model.setViewName("c-regions");
        return model;
    }

    @RequestMapping(value = "/c-zonas", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadZonas() {
        ModelAndView model = new ModelAndView();
        model.setViewName("c-zonas");
        return model;
    }

    @RequestMapping(value = "/c-groups", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadGroups() {
        ModelAndView model = new ModelAndView();
        model.setViewName("c-groups");
        return model;
    }

    @RequestMapping(value = "/request-spending", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadRequestSpending() {
        ModelAndView model = new ModelAndView();
        model.setViewName("request-spending");
        return model;
    }

    @RequestMapping(value = "/request-managment-spending", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadRequestManagmentSpending() {
        ModelAndView model = new ModelAndView();
        model.setViewName("request-managment-spending");
        return model;
    }

    @RequestMapping(value = "/capture-request-buy", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadCaptureRequestBuy() {
        ModelAndView model = new ModelAndView();
        model.setViewName("capture-request-buy");
        return model;
    }

    @RequestMapping(value = "/requests-detail", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView requestDetailUser(@RequestParam(name = "idRequest") Integer idRequest) {
        ModelAndView model = new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.setViewName("requests-detail-user");
        return model;
    }

    @RequestMapping(value = "/request-pending-autorization", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadRequestPendingAutorization() {
        ModelAndView model = new ModelAndView();
        model.setViewName("request-autorization");
        return model;
    }

    @RequestMapping(value = "/requests-off-budget", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView requestOffBudget(@RequestParam(name = "idRequest") Integer idRequest) {
        ModelAndView model = new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.setViewName("off-budget-request");
        return model;
    }

    @RequestMapping(value = "/inbox-treasury", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView siadInboxTreasury() {
        ModelAndView model = new ModelAndView();
        model.setViewName("inbox-treasury");
        return model;
    }
}
