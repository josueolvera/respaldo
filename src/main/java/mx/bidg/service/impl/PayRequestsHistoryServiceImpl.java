package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.PayRequestsHistoryDao;
import mx.bidg.model.PayRequestsHistory;
import mx.bidg.model.Users;
import mx.bidg.service.PayRequestsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by User on 21/06/2017.
 */
@Service
@Transactional
public class PayRequestsHistoryServiceImpl implements PayRequestsHistoryService{

    @Autowired
    PayRequestsHistoryDao payRequestsHistoryDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public PayRequestsHistory saveData(String data, Users user) throws IOException {
        JsonNode node = mapper.readTree(data);
        PayRequestsHistory payRequestsHistory = new PayRequestsHistory();

        for(JsonNode jsonNode : node.get("requestsSelected")){
            payRequestsHistory.setIdRequest(jsonNode.get("idRequest").asInt());
            payRequestsHistory.setIdCostCenter(jsonNode.get("distributorCostCenter").get("idCostCenter").asInt());
            payRequestsHistory.setIdRequestCategory(jsonNode.get("idRequestCategory").asInt());
            payRequestsHistory.setIdProvider(jsonNode.get("purchaseInvoices").get("idProvider").asInt());
            payRequestsHistory.setIdAccount(jsonNode.get("purchaseInvoices").get("account").get("idAccount").asInt());
            payRequestsHistory.setIdPurchaseInvoices(jsonNode.get("purchaseInvoices").get("idPurchaseInvoices").asInt());
            payRequestsHistory.setIdRequestsDates(jsonNode.get("requestsDates").get("idRequestsDates").asInt());
            payRequestsHistory.setIdDistributorDetailBank(jsonNode.get("bank").get("idDistributorDetailBank").asInt());

            payRequestsHistory.setFolio(jsonNode.get("folio").asText());
            payRequestsHistory.setCostCenter(jsonNode.get("distributorCostCenter").get("costCenter").get("name").asText());
            payRequestsHistory.setRequestCategory(jsonNode.get("requestCategory").get("requestCategoryName").asText());
            payRequestsHistory.setProvider(jsonNode.get("purchaseInvoices").get("provider").get("providerName").asText());
            payRequestsHistory.setBankProvider(jsonNode.get("purchaseInvoices").get("account").get("bank").get("bankName").asText());
            payRequestsHistory.setAccountNumber(jsonNode.get("purchaseInvoices").get("account").get("accountNumber").asText());
            payRequestsHistory.setAccountClabe(jsonNode.get("purchaseInvoices").get("account").get("accountClabe").asText());
            payRequestsHistory.setPurchaseInvoiceFolio(jsonNode.get("purchaseInvoices").get("folio").asText());
            payRequestsHistory.setAmountWithIva(jsonNode.get("purchaseInvoices").get("amountWithIva").decimalValue());
            LocalDateTime requestDate = LocalDateTime.parse(jsonNode.get("requestsDates").get("scheduledDateFormats").get("iso").asText(), DateTimeFormatter.ISO_DATE_TIME);
            payRequestsHistory.setRequestDate(requestDate);
            payRequestsHistory.setBankDistributor(jsonNode.get("bank").get("banks").get("bankName").asText());
            payRequestsHistory.setUsername(user.getUsername());
            payRequestsHistory.setCreationDate(LocalDateTime.now());
        }

        payRequestsHistory = payRequestsHistoryDao.save(payRequestsHistory);
        return payRequestsHistory;
    }
}
