package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestsServiceImpl implements RequestsService {

    @Autowired
    RequestsDao requestsDao;

    @Autowired
    AccountingAccountsDao accountingAccountsDao;

    @Autowired
    RequestTypesProductDao requestTypesProductDao;

    @Autowired
    CRequestCategoriesDao cRequestCategoriesDao;

    @Autowired
    UsersDao usersDao;

    @Autowired
    BudgetsDao budgetsDao;

    @Autowired
    RealBudgetSpendingDao budgetYearConceptDao;

    @Autowired
    CMonthsDao cMonthsDao;

    @Autowired
    FoliosService foliosService;

    @Autowired
    RequestProductsDao requestProductsDao;

    @Autowired
    PriceEstimationsDao priceEstimationsDao;

    @Autowired
    PeriodicPaymentsDao periodicPaymentsDao;

    @Autowired
    AccountsPayableDao accountsPayableDao;

    @Autowired
    CRequestStatusDao cRequestStatusDao;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DistributorCostCenterDao distributorCostCenterDao;

    @Autowired
    private RequestHistoryService requestHistoryService;

    @Autowired
    private RequestOrderDocumentsDao requestOrderDocumentsDao;

    @Autowired
    private RequestOrderDetailDao requestOrderDetailDao;

    @Autowired
    private PriceEstimationsDao getPriceEstimationsDao;

    @Autowired
    private RequestBudgetSpendingDao requestBudgetSpendingDao;

    @Autowired
    private DistributorAreaRolDao distributorAreaRolDao;

    @Override
    public HashMap<String, Object> getBudgetMonthProductType(String data) throws Exception {

        JsonNode jsonRequest = mapper.readTree(data);
        HashMap<String, Object> hashMap = new HashMap<>();

        CRequestsCategories cRequestsCategory = cRequestCategoriesDao
                .findById(jsonRequest.get("idRequestCategory").asInt());

        AccountingAccounts accountingAccounts = accountingAccountsDao.findById(jsonRequest.get("idProductType").asInt());

        Users userResponsable = usersDao.findByIdFetchDwEmployee(jsonRequest.get("idUserResponsable").asInt());
        LocalDateTime date = LocalDateTime.now();

        CMonths month = cMonthsDao.findById(date.getMonthValue());
        Integer year = date.getYear();

        if(month == null) {
            throw new ValidationException("No existe el mes", "Error al obtener el mes");
        }

        DwEnterprises dwEnterprise = userResponsable.getDwEmployee().getDwEnterprise();

        Budgets budget = budgetsDao.findByCombination(dwEnterprise.getDistributor(), dwEnterprise.getArea(),
                accountingAccounts);

        if(budget == null) {
            throw new ValidationException("No existe el Presupuesto", "No existe un presupuesto para esta solicitud");
        }

        RequestTypesProduct requestTypesProduct = requestTypesProductDao.findByCombination(cRequestsCategory,
                accountingAccounts);

        if(requestTypesProduct == null) {
            throw new ValidationException("No existe el RequestTypesProduct", "No existe un tipo de producto "
                    + "asociado a esta solicitud");
        }

        hashMap.put("requestTypesProduct", requestTypesProduct);
        RealBudgetSpending realBudgetSpending = budgetYearConceptDao.findByCombination(budget, month, dwEnterprise, year);

        if(realBudgetSpending == null) {
            throw new ValidationException("No existe Presupuesto para la fecha solicitada", "No existe Presupuesto para la fecha solicitada");
        }

        hashMap.put("realBudgetSpending", realBudgetSpending);

        return hashMap;
    }


    @Override
    public Requests saveData(String data, Users user) throws Exception {

        JsonNode jsonRequest = mapper.readTree(data);
        Requests request = new Requests();

        String justify = (jsonRequest.get("request").get("purpose").asText());
        int idCostCenter = jsonRequest.get("request").get("idCostCenter").asInt();
        int idAccountingAccounts = jsonRequest.get("request").get("idAccountingAccount").asInt();


        DistributorCostCenter distributorCostCenter = distributorCostCenterDao.findByIdCostCenterAndAA(idCostCenter, idAccountingAccounts);


        //51 es el id de Requests en CTables
        request.setFolio(foliosService.createNew(new CTables(51)));
        request.setRequestStatus(CRequestStatus.ENVIADA_PENDIENTE);
        request.setRequestCategory(CRequestsCategories.SOLICITUD);
        request.setRequestType(CRequestTypes.VIGENTES);
        request.setDistributorCostCenter(distributorCostCenter);
        request.setIdAccessLevel(1);
        request.setTotalExpended(new BigDecimal(0.00));
        request.setReason(justify);

        if (user != null){
            request.setUserName(user.getUsername());
            if (user.getDwEmployee() != null){
                if (user.getDwEmployee().getEmployee() != null){
                    request.setEmployees(user.getDwEmployee().getEmployee());
                }
            }
        }

        request.setCreationDate(LocalDateTime.now());
        request.setIdAccessLevel(1);
        List<RequestProducts> requestProducts = new ArrayList<>();

        for(JsonNode jsonProducts : jsonRequest.get("products")) {
            RoleProductRequest roleProductRequest = new RoleProductRequest(jsonProducts.get("productBuy").get("idRoleProductRequest").asInt());

            RequestProducts requestProduct = new RequestProducts();

            requestProduct.setRoleProductRequest(roleProductRequest);
            requestProduct.setRequest(request);
            requestProduct.setIdAccessLevel(1);
            requestProduct.setCreationDate(LocalDateTime.now());
            requestProduct.setUsername(user.getUsername());
            requestProduct.setQuantity(jsonProducts.get("quantity").asInt());
            requestProducts.add(requestProduct);
        }

        request.setRequestProductsList(requestProducts);
        request = requestsDao.save(request);

        EmailTemplates emailTemplates = emailTemplatesService.findByName("new_request_notification");
        emailTemplates.addProperty("request", request);
        emailDeliveryService.deliverEmailWithUser(emailTemplates, user);

        EmailTemplates emailTemplates1 = emailTemplatesService.findByName("request_autorization_notification");
        emailTemplates1.addProperty("request", request);
        emailDeliveryService.deliverEmail(emailTemplates1);

        requestHistoryService.saveRequest(request, user);

        return request;
    }

    @Override
    public Requests authorization(Integer idRequest) {

        Requests request = requestsDao.findByIdFetchCategory(idRequest);
        if(request == null) {
            throw new ValidationException("No existe el request", "No se encuentra la solicitud", HttpStatus.CONFLICT);
        }

//        if((request.getIdRequestStatus() != CRequestStatus.COTIZADA.getIdRequestStatus())
//                && request.getIdRequestStatus() != CRequestStatus.PENDIENTE.getIdRequestStatus()) {
//            throw new ValidationException("Estatus de la solicitud invalida", "Esta solicitud ya fue validada anteriormente", HttpStatus.CONFLICT);
//        }

        boolean estimationAccepted = false;
        List<PriceEstimations> estimations = priceEstimationsDao.findByIdRequest(idRequest);
        if(estimations.isEmpty()) {
            throw new ValidationException("Esta solicitud no tiene cotizaciones", "Necesita agregar cotizaciones antes de " +
                    "autorizar una solicitud", HttpStatus.CONFLICT);
        }

        for(PriceEstimations estimation : estimations) {
            if(estimation.getIdEstimationStatus() == CEstimationStatus.APROBADA.getIdEstimationStatus()) {
                estimationAccepted = true;
                break;
            }
        }

        if(! estimationAccepted) {
            throw new ValidationException("Esta solicitud no tiene cotizaciones aprobadas", "Necesita aprobar una cotizacion " +
                    "antes de autorizar una solicitud", HttpStatus.CONFLICT);
        }

        PeriodicsPayments periodicsPayment = periodicPaymentsDao.findByFolio(request.getFolio());
        List<AccountsPayable> accountslist = accountsPayableDao.findByFolio(request.getFolio());

//        if((request.getRequestTypeProduct().getIdRequestCategory() == CRequestsCategories.PERIODICA && periodicsPayment == null) ||
//                (request.getRequestTypeProduct().getIdRequestCategory() == CRequestsCategories.COTIZABLE && accountslist.isEmpty()) ||
//                (request.getRequestTypeProduct().getIdRequestCategory() == CRequestsCategories.DIRECTA && accountslist.isEmpty())) {
//            throw new ValidationException("No hay CXP referentes a esta solicitud", "Necesita agregar informacion de pago para esta " +
//                    "solicitud", HttpStatus.CONFLICT);
//        }

        if (accountslist.size() > 0) {
            for ( AccountsPayable accountPayable : accountslist) {
                accountPayable.setAccountPayableStatus(CAccountsPayableStatus.PENDIENTE);
            }
        }

        if (periodicsPayment != null) {
            periodicsPayment.setPeriodicPaymentStatus(CPeriodicPaymentsStatus.ACTIVO);
        }
//        request.setRequestStatus(CRequestStatus.APROBADA);
        return requestsDao.update(request);
    }

    @Override
    public Requests findById(Integer idRequest) {
        return requestsDao.findById(idRequest);
    }

    @Override
    public Requests findByFolio(String folio) {
        return requestsDao.findByFolio(folio);
    }

    @Override
    public EmailTemplates sendEmailForNewRequest(Requests request) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("new_request_notification");
//        String typeRequest = request.getRequestTypeProduct().getRequestCategory().getCategory();
//        String typeRequestMinus = typeRequest.toLowerCase();
        emailTemplate.addProperty("request", request);
//        emailTemplate.addProperty("typeRequest", typeRequestMinus);
//        emailTemplate.addRecipient(new EmailRecipients(request.getUserRequest().getMail(), request.getUserRequest().getUsername(), EmailRecipients.TO));
        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public EmailTemplates sendEmailForNewRequestAuthorization(Requests request, Users user) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("request_autorization_notification");
//        String typeRequest = request.getRequestTypeProduct().getRequestCategory().getCategory();
//        String typeRequestMinus = typeRequest.toLowerCase();
        emailTemplate.addProperty("request", request);
//        emailTemplate.addProperty("typeRequest", typeRequestMinus);
        emailTemplate.addRecipient(new EmailRecipients(user.getMail(), user.getUsername(), EmailRecipients.TO));
        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public Requests changeActiveStatus(Integer idRequest) {

        Requests request = requestsDao.findById(idRequest);

//        if (request != null) {
//            request.setActive(!request.getActive());
//
//            request = requestsDao.update(request);
//        }

        return request;
    }

    @Override
    public List<Requests> findByRequestCategory(Integer idRequestCategory) {
        return requestsDao.findByRequestCategory(idRequestCategory);
    }

    @Override
    public List<Requests> findByCategoryAndTypeByEmployee(Integer idRequestCategory, Integer idRequestType, Integer idEmployee) {
        return requestsDao.findByCategoryAndTypeByEmployee(idRequestCategory, idRequestType, idEmployee);
    }

    @Override
    public List<Requests> findByCategoryAndType(Integer idRequestCategory, Integer idRequestType) {
        return requestsDao.findByCategoryAndType(idRequestCategory, idRequestType);
    }

    @Override
    public boolean deleteRequest(Integer idRequest) {

        Requests request = requestsDao.findById(idRequest);

        boolean aux = false;
        if (request != null){
            List<PriceEstimations> priceEstimationsList = priceEstimationsDao.findByIdRequest(request.getIdRequest());

            if (!priceEstimationsList.isEmpty()){
                for (PriceEstimations priceEstimations : priceEstimationsList){
                    priceEstimationsDao.delete(priceEstimations);
                }
            }

            List<RequestProducts> requestProductsList = requestProductsDao.findByIdRequest(request.getIdRequest());

            if (!requestProductsList.isEmpty()){
                for (RequestProducts requestProducts : requestProductsList){
                    requestProductsDao.delete(requestProducts);
                }
            }

            RequestOrderDocuments requestOrderDocuments = requestOrderDocumentsDao.findByIdRequest(request.getIdRequest());

            if (requestOrderDocuments != null){


                List<RequestOrderDetail> requestOrderDetails = requestOrderDetailDao.findByidReqOrderDocument(requestOrderDocuments.getIdRequestOrderDocument());

                if (!requestOrderDetails.isEmpty()){
                    for (RequestOrderDetail requestOrderDetail : requestOrderDetails){
                        requestOrderDetailDao.delete(requestOrderDetail);
                    }
                }

                requestOrderDocumentsDao.delete(requestOrderDocuments);
            }

            aux = requestsDao.delete(request);
        }
        return aux;
    }

    @Override
    public List<Requests> findByCategoryAndTypeAndStatus(Integer idRequestCategory, Integer idRequestType) {
        return requestsDao.findByCategoryAndTypeAndStatus(idRequestCategory, idRequestType);
    }

    @Override
    public Requests rejectRequest(Integer idRequest, String rejectJustify, Users user) {
        Requests request = requestsDao.findById(idRequest);

        if (request != null){
            request.setRequestStatus(CRequestStatus.RECHAZADA);
            request.setRequestType(CRequestTypes.RECHAZADA);
            request.setReasonResponsible(rejectJustify);
            request = requestsDao.update(request);

            requestHistoryService.saveRequest(request, user);
        }
        return request;
    }

    @Override
    public Requests update(Requests request) {
        return requestsDao.update(request);
    }

    @Override
    public List<Requests> findByDCC(List<Integer> idDCCs){
        return requestsDao.findByDCC(idDCCs);
    }

    @Override
    public List<Requests> findAll(){
        return requestsDao.findAll();
    }

    @Override
    public Requests payRequest(Integer idRequest) {
        Requests request = requestsDao.findById(idRequest);
        CRequestStatus cRequestStatus = cRequestStatusDao.findById(6);

        if (request != null) {
            request.setRequestStatus(cRequestStatus);
            request = requestsDao.update(request);
        }

        return request;
    }

    @Override
    public List<Requests> findListByFolio(String folio){
        return requestsDao.findListByFolio(folio);
    }

    @Override
    public List<Requests> findByStatus(Integer idRequestStatus) {
        return requestsDao.findByStatus(idRequestStatus);
    }

    @Override
    public Requests sendToBuyMAnagementRequest(Integer idRequest, Users user) {
        Requests request = requestsDao.findById(idRequest);

        if(request != null){
            request.setRequestStatus(CRequestStatus.EN_PROCESO_DE_COMPRA);
            request = requestsDao.update(request);

            requestHistoryService.saveRequest(request, user);



            PriceEstimations priceEstimation = priceEstimationsDao.findAuthorized(request.getIdRequest());

            Calendar calendar = Calendar.getInstance();
            int año = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;

            RequestBudgetSpending requestBudgetSpending = requestBudgetSpendingDao.findByIdDistributorCostCenterYear(request.getIdDistributorCostCenter(), año);

            if (requestBudgetSpending != null){
                switch (month){
                    case 1:
                        BigDecimal spendedAmount = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended = null;
                        if (spendedAmount != null){
                            totalSpended = spendedAmount.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setJanuarySpended(totalSpended);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 2:
                        BigDecimal spendedAmount2 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended2 = null;
                        if (spendedAmount2 != null){
                            totalSpended2 = spendedAmount2.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended2 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setFebruarySpended(totalSpended2);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 3:
                        BigDecimal spendedAmount3 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended3 = null;
                        if (spendedAmount3 != null){
                            totalSpended3 = spendedAmount3.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended3 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setMarchSpended(totalSpended3);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 4:
                        BigDecimal spendedAmount4 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended4 = null;
                        if (spendedAmount4 != null){
                            totalSpended4 = spendedAmount4.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended4 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setAprilSpended(totalSpended4);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 5:
                        BigDecimal spendedAmount5 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended5 = null;
                        if (spendedAmount5 != null){
                            totalSpended5 = spendedAmount5.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended5 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setMaySpended(totalSpended5);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 6:
                        BigDecimal spendedAmount6 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended6 = null;
                        if (spendedAmount6 != null){
                            totalSpended6 = spendedAmount6.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended6 = priceEstimation.getAmount();
                        }

                        requestBudgetSpending.setJuneSpended(totalSpended6);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 7:
                        BigDecimal spendedAmount7 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended7 = null;
                        if (spendedAmount7 != null){
                            totalSpended7 = spendedAmount7.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended7 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setJulySpended(totalSpended7);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 8:
                        BigDecimal spendedAmount8 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended8 = null;
                        if (spendedAmount8 != null){
                            totalSpended8 = spendedAmount8.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended8 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setAugustSpended(totalSpended8);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 9:
                        BigDecimal spendedAmount9 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended9 = null;
                        if (spendedAmount9 != null){
                            totalSpended9 = spendedAmount9.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended9 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setSeptemberSpended(totalSpended9);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 10:
                        BigDecimal spendedAmount10 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended10 = null;
                        if (spendedAmount10 != null){
                            totalSpended10 = spendedAmount10.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended10 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setOctoberSpended(totalSpended10);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 11:
                        BigDecimal spendedAmount11 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended11 = null;
                        if (spendedAmount11 != null){
                            totalSpended11 = spendedAmount11.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended11 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setNovemberSpended(totalSpended11);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;
                    case 12:
                        BigDecimal spendedAmount12 = requestBudgetSpendingDao.getAmountExpendedDistributorCostCenter(request.getIdDistributorCostCenter(), month, año);
                        BigDecimal totalSpended12 = null;
                        if (spendedAmount12 != null){
                            totalSpended12 = spendedAmount12.add(priceEstimation.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }else{
                            totalSpended12 = priceEstimation.getAmount();
                        }
                        requestBudgetSpending.setDecemberSpended(totalSpended12);
                        requestBudgetSpendingDao.update(requestBudgetSpending);
                        break;

                }
            }

        }
        return request;
    }

    @Override
    public List<Requests> findByAuthorizedAmounAndStatus(Users user) {

        List<Requests> requests = null;
        if(user != null){
            if (user.getDwEmployee() != null){
                DistributorAreaRol distributorAreaRol = distributorAreaRolDao.findByCombination(user.getDwEmployee().getDwEnterprise().getDistributor().getIdDistributor(), user.getDwEmployee().getDwEnterprise().getArea().getIdArea(), user.getDwEmployee().getRole().getIdRole());
                requests = requestsDao.findByTotalExpended(distributorAreaRol.getAmountRequest());
            }
        }

        return requests;
    }

    @Override
    public Requests sendToFinancialPlaningRequest(Integer idRequest, Users user) {
        Requests request = requestsDao.findById(idRequest);

        if (request != null){
            request.setRequestStatus(CRequestStatus.EN_PROCESO_DE_VALIDACION_POR_PLANEACION);
            request = requestsDao.update(request);

            requestHistoryService.saveRequest(request, user);

            EmailTemplates emailTemplates = emailTemplatesService.findByName("financial_p_autorization_notification");
            emailTemplates.addProperty("request", request);
            emailDeliveryService.deliverEmail(emailTemplates);
        }
        return request;
    }
}
