package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
    public boolean validatePriceEstimations(Integer idRequest, Integer idPriceEstimations) {

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
                            outBudget = true;

                        }else {
                            outBudget = false;
                        }
                    }
                }
            }
        }

        return outBudget;
    }
}
