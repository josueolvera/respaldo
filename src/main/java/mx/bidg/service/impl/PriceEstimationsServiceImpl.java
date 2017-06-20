package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.pojos.FilePojo;
import mx.bidg.service.PriceEstimationsService;
import mx.bidg.service.RealBudgetSpendingService;
import mx.bidg.service.RequestHistoryService;
import mx.bidg.utils.BudgetHelper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PriceEstimationsServiceImpl implements PriceEstimationsService {

    @Autowired
    private PriceEstimationsDao priceEstimationsDao;

    @Autowired
    private PeriodicPaymentsDao periodicPaymentsDao;

    @Autowired
    private AccountsPayableDao accountsPayableDao;

    @Autowired
    private RequestsDao requestsDao;

    @Autowired
    private CCurrenciesDao currenciesDao;

    @Autowired
    private AccountsDao accountsDao;

    @Autowired
    private ProvidersAccountsDao providersAcountsdao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BudgetHelper budgetHelper;

    @Autowired
    private Environment env;

    @Autowired
    private RealBudgetSpendingService realBudgetSpendingService;

    @Autowired
    private BudgetsDao budgetsDao;

    @Autowired
    private RequestHistoryService requestHistoryService;

    @Autowired
    private RequestBudgetSpendingDao requestBudgetSpendingDao;

    @Autowired
    private DwEmployeesDao dwEmployeesDao;

    @Autowired
    private DistributorAreaRolDao distributorAreaRolDao;

    @Autowired
    private EmployeesHistoryDao employeesHistoryDao;

    @Autowired
    private UsersDao usersDao;

    @Override
    public PriceEstimations saveData(String data, Integer idRequest, Users user) throws Exception {

        String SAVE_PATH = env.getRequiredProperty("estimations.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("estimations.attachments.media_types").split(",");

        JsonNode node = mapper.readTree(data);

        Requests request = requestsDao.findById(idRequest);

        if (request != null) {
            PriceEstimations priceEstimation = new PriceEstimations();

            priceEstimation.setRequest(request);
            priceEstimation.setcEstimationStatus(CEstimationStatus.PENDIENTE);
            priceEstimation.setAmount(new BigDecimal(node.get("amount").asText().replace(",","")));
            FilePojo file = mapper.treeToValue(node.get("file"), FilePojo.class);
            priceEstimation.setFileName(file.getName());
            priceEstimation.setIdAccessLevel(1);
            priceEstimation.setUsername(user.getUsername());
            priceEstimation.setCreationDate(LocalDateTime.now());

            //priceEstimation = priceEstimationsDao.save(priceEstimation);

            boolean isValidMediaType = false;

            for (String mediaType : fileMediaTypes) {
                if (file.getType().equals(mediaType)) {
                    isValidMediaType = true;
                    break;
                }
            }

            if (! isValidMediaType) {
                throw new ValidationException("Tipo de archivo no admitido", "Tipo de archivo no admitido");
            }

            String destDir = "/estimation_" + idRequest;
            String destFile = destDir + "/Documento." + priceEstimation.getCreationDate().toInstant(ZoneOffset.UTC).getEpochSecond()+(int) (Math.random() * 30) + 1;

            priceEstimation.setFilePath(destFile);

            File dir = new File(SAVE_PATH + destDir);
            if (! dir.exists()) {
                dir.mkdir();
            }

            String encodingPrefix = "base64,";
            int contentStartIndex = file.getDataUrl().indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] byteArreyData = Base64.decodeBase64(file.getDataUrl().substring(contentStartIndex));

            FileOutputStream out = new FileOutputStream(new File(SAVE_PATH + destFile));
            out.write(byteArreyData);
            out.close();

            priceEstimation = priceEstimationsDao.save(priceEstimation);

            return priceEstimation;
        }

        return null;
    }

    @Override
    public PriceEstimations findById(int id) {
        return priceEstimationsDao.findById(id);
    }

    @Override
    public PriceEstimations update(Integer idEstimation, String data, Users user) throws Exception{
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        JsonNode json = mapper.readTree(data);
//        if(estimation.getEstimationStatus().getIdEstimationStatus().equals(CEstimationStatus.PENDIENTE)) {
        Requests request = requestsDao.findByIdFetchBudgetMonthBranch(estimation.getIdRequest());
//            BigDecimal budgetAmount = request.getBudgetYearConcept().getAmount();
//            BigDecimal expendedAmount = request.getBudgetYearConcept().getExpendedAmount();
//            BigDecimal residualAmount = budgetAmount.subtract(expendedAmount);
        BigDecimal rate = ((json.get("rate").decimalValue().compareTo(BigDecimal.ZERO)) == 1)? json.get("rate").decimalValue() : BigDecimal.ONE;
        BigDecimal amount = ((json.get("amount").decimalValue().compareTo(BigDecimal.ZERO)) == 1)?
                json.get("amount").decimalValue() : BigDecimal.ZERO;
        BigDecimal tempAmount = amount.multiply(rate);

//            estimation.setAccount(new Accounts(json.get("idAccount").asInt()));
        estimation.setAmount(amount);
//            estimation.setCurrency(new CCurrencies(json.get("idCurrency").asInt()));
//            estimation.setRate(rate);
//            estimation.setUserEstimation(user);
        //Si el Monto de Presupuesto es menor al de la cotizacion, OutOfBudget = true
//            estimation.setOutOfBudget((residualAmount.compareTo(tempAmount) == -1)? 1 : 0);

        return estimation;
//        } else {
//            throw new ValidationException("No se puede modificar una cotizacion ya autorizada",
//                    "No se puede modificar una solicitud ya autorizada");
//        }
    }

    @Override
    public List<PriceEstimations> findByIdRequest(int idRequest) {
        return priceEstimationsDao.findByIdRequest(idRequest);
    }

    @Override
    public PriceEstimations estimationAuthorization(int idEstimation, Users user) {
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        Requests request = estimation.getRequest();

//        if (request.getIdRequestStatus().equals(CRequestStatus.APROBADA.getIdRequestStatus())
//                || request.getIdRequestStatus().equals(CRequestStatus.RECHAZADA.getIdRequestStatus())) {
//            throw new ValidationException("No es posible elegir una cotizacion de una Solicitud Aceptada o Rechazada",
//                    "No es posible elegir una cotizacion de una Solicitud Aceptada o Rechazada", HttpStatus.FORBIDDEN);
//        } else {
//            String folio = request.getFolio();
//            PeriodicsPayments periodicPayment = periodicPaymentsDao.findByFolio(folio);
//            if ((periodicPayment != null) &&
//                    (periodicPayment.getIdPeriodicPaymentStatus().equals(CPeriodicPaymentsStatus.INACTIVO))) {
//                if(!periodicPaymentsDao.delete(periodicPayment))
//                    throw new ValidationException("No se pudo eliminar el PeriodicPayment: " + periodicPayment);
//            }
//
//            List<AccountsPayable> accountsPayable = accountsPayableDao.findByFolio(folio);
//            for(AccountsPayable account : accountsPayable) {
//                if(account.getAccountPayableStatus().getIdAccountPayableStatus().equals(CAccountsPayableStatus.INACTIVA)) {
//                    if(!accountsPayableDao.delete(account))
//                        throw new ValidationException("No se pudo eliminar el AccountPayable: " + account);
//                }
//            }
//
//            List<PriceEstimations> list = priceEstimationsDao.findByIdRequest(request.getIdRequest());
//
//            for (PriceEstimations e : list) {
////                e.setUserAuthorization(user);
//                e.setAuthorizationDate(LocalDateTime.now());
//
////                if (e.getIdEstimation().equals(idEstimation)) {
////                    e.setEstimationStatus(CEstimationStatus.APROBADA);
////                } else {
////                    e.setEstimationStatus(CEstimationStatus.RECHAZADA);
////                }
//            }
//        }

        return estimation;
    }

    @Override
    public boolean reject(Integer idEstimation) {
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        Requests request = estimation.getRequest();
        List<PriceEstimations> estimations = priceEstimationsDao.findByIdRequest(request.getIdRequest());

        for (PriceEstimations e : estimations) {
//            e.setEstimationStatus(CEstimationStatus.PENDIENTE);
        }
        accountsPayableDao.deleteByFolio(request.getFolio());
        return true;
    }

    @Override
    public boolean delete(Integer idEstimation) {
        PriceEstimations estimation = priceEstimationsDao.findByIdFetchRequestStatus(idEstimation);
        if(estimation.getIdEstimationStatus() == CEstimationStatus.PENDIENTE.getIdEstimationStatus()){
            return priceEstimationsDao.delete(estimation);
        } else {
            throw new ValidationException("La cotizacion no tiene estatus de Pendiente", "Solo pueden" +
                    " eliminarse Cotizaciones Pendientes de aprobacion", HttpStatus.CONFLICT);
        }
    }

    @Override
    public PriceEstimations saveFileData(int idEstimation, String fileName, String filePath) {
        PriceEstimations estimation = priceEstimationsDao.findById(idEstimation);
        estimation.setFileName(fileName);
        estimation.setFilePath(filePath);
        return priceEstimationsDao.update(estimation);
    }

    @Override
    public PriceEstimations findAuthorized(Integer idRequest) {
        PriceEstimations estimation = priceEstimationsDao.findAuthorized(idRequest);
//        ProvidersAccounts providersAccounts = providersAcountsdao.findByAccount(estimation.getAccount());
        ArrayList<ProvidersAccounts> providers = new ArrayList<>();
//        providers.add(providersAccounts);
//        estimation.getAccount().setProvidersAccountsList(providers);
        return estimation;
    }

    @Override
    public boolean authorizePriceEstimations(Integer idRequest, Integer idPriceEstimations, String reasonResponsible,  Users user) {
        List<PriceEstimations> priceEstimations = priceEstimationsDao.findEstimationsNotSelectedByRequest(idRequest, idPriceEstimations);

        PriceEstimations priceEstimation = priceEstimationsDao.findByIdFetchRequestStatus(idPriceEstimations);

        Requests request = requestsDao.findByIdFetchStatus(idRequest);

        boolean outBudget = false;

        if (request != null){
            if (request.getIdDistributorCostCenter() != null){
                List<Budgets> budgetsList = budgetsDao.findByIdDistributor(request.getIdDistributorCostCenter());
                if (!budgetsList.isEmpty()){
                    BigDecimal budgetAmount = realBudgetSpendingService.getAmountBudget(budgetsList);
                    if (budgetAmount != null){
                        if (priceEstimation.getAmount().doubleValue() >= budgetAmount.doubleValue()){
                            request.setRequestStatus(CRequestStatus.EN_PROCESO_DE_VALIDACION_POR_PLANEACION);
                            request.setTotalExpended(priceEstimation.getAmount());
                            request.setReasonResponsible(reasonResponsible);
                            request = requestsDao.update(request);
                            requestHistoryService.saveRequest(request, user);
                            outBudget = true;

                        }else {
                            request.setRequestStatus(CRequestStatus.EN_PROCESO_DE_COMPRA);
                            request.setTotalExpended(priceEstimation.getAmount());
                            request.setReasonResponsible(reasonResponsible);
                            request = requestsDao.update(request);
                            requestHistoryService.saveRequest(request, user);
                            outBudget = false;
                        }

                        if (!priceEstimations.isEmpty()){
                            for (PriceEstimations estimation : priceEstimations){
                                estimation.setcEstimationStatus(CEstimationStatus.RECHAZADA);
                                priceEstimationsDao.update(estimation);
                            }
                        }

                        if (priceEstimation != null){
                            priceEstimation.setcEstimationStatus(CEstimationStatus.APROBADA);
                            priceEstimationsDao.update(priceEstimation);
                        }
                    }
                }
            }
        }

        return outBudget;
    }

    @Override
    public int validatePriceEstimations(Integer idRequest, Integer idPriceEstimations) {

        PriceEstimations priceEstimation = priceEstimationsDao.findByIdFetchRequestStatus(idPriceEstimations);

        Requests request = requestsDao.findByIdFetchStatus(idRequest);

        int outBudget = 0;

        if (request != null){
            if (request.getRequestStatus().getIdRequestStatus() == 1){
                if (request.getEmployees() != null){
                    DwEmployees dwEmployee = dwEmployeesDao.findByIdEmployee(request.getEmployees().getIdEmployee());

                    DistributorAreaRol distributorAreaRol = distributorAreaRolDao.findByCombination(dwEmployee.getDwEnterprise().getIdDistributor(), dwEmployee.getDwEnterprise().getIdArea(), dwEmployee.getIdRole());

                    if (distributorAreaRol != null){

                        if (priceEstimation.getAmount().doubleValue() >= distributorAreaRol.getAmountRequest().doubleValue()){
                            int aux;

                            aux = distributorAreaRol.getLevelRequest() - 1;

                            if (aux == 3){
                                aux = aux - 1;
                            }

                            while (true){
                                DistributorAreaRol dis = distributorAreaRolDao.findByLevel(aux);
                                if(dis.getAmountRequest().doubleValue() >= priceEstimation.getAmount().doubleValue()){
                                    break;
                                }else {
                                    aux--;
                                }
                            }

                            DistributorAreaRol levelWithAuthorization = distributorAreaRolDao.findByLevel(aux);

                            EmployeesHistory employeesHistory = employeesHistoryDao.findByDistributorAreaRolLastRegister(levelWithAuthorization.getIdDistributor(), levelWithAuthorization.getIdArea(), levelWithAuthorization.getIdRole());

                            if (employeesHistory != null){
                                Users user = usersDao.findByDwEmpployee(employeesHistory.getIdDwEmployee());

                                outBudget = 1;

                                System.out.println("Se va a usuario por nivel: " + user);
                                System.out.println("Empleado: " + employeesHistory);
                                System.out.println("variable: " + outBudget);
                            }


                        }else {

                            outBudget = validateBudget(request, priceEstimation);
                        }
                    }
                }
            }else if (request.getRequestStatus().getIdRequestStatus() == 5){

                outBudget = validateBudget(request, priceEstimation);

            }else if (request.getRequestStatus().getIdRequestStatus() == 4){

                outBudget = 3;
            }
        }

        return outBudget;
    }




    public int validateBudget(Requests request, PriceEstimations priceEstimation){
        int outBudget = 0;
        if (request.getIdDistributorCostCenter() != null){
            Calendar date = Calendar.getInstance();
            int año = date.get(Calendar.YEAR);
            RequestBudgetSpending requestBudgetSpending = requestBudgetSpendingDao.findByIdDistributorCostCenterYear(request.getIdDistributorCostCenter(), año);
            if (requestBudgetSpending != null){

                List<Budgets> budgetsList = budgetsDao.findByIdDistributor(request.getIdDistributorCostCenter());
                BigDecimal budgetAmount = null;
                if (!budgetsList.isEmpty()){
                    budgetAmount = realBudgetSpendingService.getAmountBudget(budgetsList);
                }

                int month = date.get(Calendar.MONTH) + 1;

                switch (month){
                    case 1:
                        requestBudgetSpending.setJanuaryAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getJanuaryAmount() != null){
                            if(requestBudgetSpending.getJanuarySpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getJanuaryAmount().add(requestBudgetSpending.getJanuarySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getJanuaryAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 2:
                        requestBudgetSpending.setFebruaryAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getFebruaryAmount() != null){
                            if(requestBudgetSpending.getFebruarySpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getFebruaryAmount().add(requestBudgetSpending.getFebruarySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getFebruaryAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 3:
                        requestBudgetSpending.setMarchAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getMarchAmount() != null){
                            if(requestBudgetSpending.getMarchSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getMarchAmount().add(requestBudgetSpending.getMarchSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getMarchAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 4:
                        requestBudgetSpending.setAprilAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getAprilAmount() != null){
                            if(requestBudgetSpending.getAprilSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getAprilAmount().add(requestBudgetSpending.getAprilSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getAprilAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 5:
                        requestBudgetSpending.setMayAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getMayAmount() != null){
                            if(requestBudgetSpending.getMaySpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getMayAmount().add(requestBudgetSpending.getMaySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getMayAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 6:
                        requestBudgetSpending.setJuneAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getJuneAmount() != null){
                            if(requestBudgetSpending.getJuneSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getJuneAmount().add(requestBudgetSpending.getJuneSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;

                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getJuneAmount().doubleValue()){
                                    outBudget = 2;


                                }else {
                                    outBudget = 3;

                                }
                            }
                        } else {
                            outBudget = 2;

                        }
                        break;
                    case 7:
                        requestBudgetSpending.setJulyAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getJulyAmount() != null){
                            if(requestBudgetSpending.getJulySpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getJulyAmount().add(requestBudgetSpending.getJulySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getJulyAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 8:
                        requestBudgetSpending.setAugustAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getAugustAmount() != null){
                            if(requestBudgetSpending.getAugustSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getAugustAmount().add(requestBudgetSpending.getAugustSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getAugustAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 9:
                        requestBudgetSpending.setSeptemberAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getSeptemberAmount() != null){
                            if(requestBudgetSpending.getSeptemberSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getSeptemberAmount().add(requestBudgetSpending.getSeptemberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getSeptemberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 10:
                        requestBudgetSpending.setOctoberAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getOctoberAmount() != null){
                            if(requestBudgetSpending.getOctoberSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getOctoberAmount().add(requestBudgetSpending.getOctoberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getOctoberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 11:
                        requestBudgetSpending.setNovemberAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getNovemberAmount() != null){
                            if(requestBudgetSpending.getNovemberSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getNovemberAmount().add(requestBudgetSpending.getNovemberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getNovemberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 12:
                        requestBudgetSpending.setDecemberAmount(budgetAmount);
                        requestBudgetSpending.setCreationDate(LocalDateTime.now());
                        requestBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        requestBudgetSpending.setYear(año);
                        requestBudgetSpending = requestBudgetSpendingDao.update(requestBudgetSpending);

                        if (requestBudgetSpending.getDecemberAmount() != null){
                            if(requestBudgetSpending.getDecemberSpended() != null){
                                BigDecimal totalAmount = requestBudgetSpending.getDecemberAmount().add(requestBudgetSpending.getDecemberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= requestBudgetSpending.getDecemberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                }
            }else {

                RequestBudgetSpending rBudgetSpending = new RequestBudgetSpending();

                List<Budgets> budgetsList = budgetsDao.findByIdDistributor(request.getIdDistributorCostCenter());
                BigDecimal budgetAmount = null;
                if (!budgetsList.isEmpty()){
                    budgetAmount = realBudgetSpendingService.getAmountBudget(budgetsList);
                }

                int month = date.get(Calendar.MONTH) + 1;

                switch (month){
                    case 1:
                        rBudgetSpending.setJanuaryAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getJanuaryAmount() != null){
                            if(rBudgetSpending.getJanuarySpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getJanuaryAmount().add(rBudgetSpending.getJanuarySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getJanuaryAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 2:
                        rBudgetSpending.setFebruaryAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getFebruaryAmount() != null){
                            if(rBudgetSpending.getFebruarySpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getFebruaryAmount().add(rBudgetSpending.getFebruarySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getFebruaryAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 3:
                        rBudgetSpending.setMarchAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getMarchAmount() != null){
                            if(rBudgetSpending.getMarchSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getMarchAmount().add(rBudgetSpending.getMarchSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getMarchAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 4:
                        rBudgetSpending.setAprilAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getAprilAmount() != null){
                            if(rBudgetSpending.getAprilSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getAprilAmount().add(rBudgetSpending.getAprilSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getAprilAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 5:
                        rBudgetSpending.setMayAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getMayAmount() != null){
                            if(rBudgetSpending.getMaySpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getMayAmount().add(rBudgetSpending.getMaySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getMayAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 6:
                        rBudgetSpending.setJuneAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getJuneAmount() != null){
                            if(rBudgetSpending.getJuneSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getJuneAmount().add(rBudgetSpending.getJuneSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;

                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getJuneAmount().doubleValue()){
                                    outBudget = 2;


                                }else {
                                    outBudget = 3;

                                }
                            }
                        } else {
                            outBudget = 2;

                        }
                        break;
                    case 7:
                        rBudgetSpending.setJulyAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getJulyAmount() != null){
                            if(rBudgetSpending.getJulySpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getJulyAmount().add(rBudgetSpending.getJulySpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getJulyAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 8:
                        rBudgetSpending.setAugustAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getAugustAmount() != null){
                            if(rBudgetSpending.getAugustSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getAugustAmount().add(rBudgetSpending.getAugustSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getAugustAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 9:
                        rBudgetSpending.setSeptemberAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getSeptemberAmount() != null){
                            if(rBudgetSpending.getSeptemberSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getSeptemberAmount().add(rBudgetSpending.getSeptemberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getSeptemberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 10:
                        rBudgetSpending.setOctoberAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getOctoberAmount() != null){
                            if(rBudgetSpending.getOctoberSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getOctoberAmount().add(rBudgetSpending.getOctoberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getOctoberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 11:
                        rBudgetSpending.setNovemberAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getNovemberAmount() != null){
                            if(rBudgetSpending.getNovemberSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getNovemberAmount().add(rBudgetSpending.getNovemberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getNovemberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                    case 12:
                        rBudgetSpending.setDecemberAmount(budgetAmount);
                        rBudgetSpending.setCreationDate(LocalDateTime.now());
                        rBudgetSpending.setDistributorCostCenter(request.getDistributorCostCenter());
                        rBudgetSpending.setYear(año);
                        rBudgetSpending = requestBudgetSpendingDao.save(rBudgetSpending);

                        if (rBudgetSpending.getDecemberAmount() != null){
                            if(rBudgetSpending.getDecemberSpended() != null){
                                BigDecimal totalAmount = rBudgetSpending.getDecemberAmount().add(rBudgetSpending.getDecemberSpended()).setScale(2, BigDecimal.ROUND_HALF_UP);

                                if (priceEstimation.getAmount().doubleValue() >= totalAmount.doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }else{
                                if (priceEstimation.getAmount().doubleValue() >= rBudgetSpending.getDecemberAmount().doubleValue()){
                                    outBudget = 2;

                                }else {
                                    outBudget = 3;
                                }
                            }
                        } else {
                            outBudget = 2;
                        }
                        break;
                }
            }
        }

        return outBudget;
    }
}
