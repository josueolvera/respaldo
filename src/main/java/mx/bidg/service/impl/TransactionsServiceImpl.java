package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.BalancesDao;
import mx.bidg.dao.TransactionsDao;
import mx.bidg.model.*;
import mx.bidg.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Service
@Transactional
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionsDao transactionsDao;

    @Autowired
    BalancesDao balancesDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Transactions findById(Integer id) {
        return transactionsDao.findById(id);
    }

    @Override
    public Transactions save(Transactions transaction) {
        transactionsDao.save(transaction);
        return transaction;
    }

    @Override
    public Transactions update(Transactions transaction) {
        transactionsDao.update(transaction);
        return transaction;
    }

    @Override
    public boolean delete(Transactions transaction) {
        transactionsDao.delete(transaction);
        return true;
    }

    @Override
    public List<Transactions> findAll() {
        return transactionsDao.findAll();
    }

    @Override
    public void entryPayAccount(String data) throws IOException {

            JsonNode node = mapper.readTree(data);

            Transactions transactions = new Transactions();
            transactions.setAccountsPayable(null);
            transactions.setAmount(node.get("amount").decimalValue());
            Balances balances = balancesDao.findById(node.get("idBalance").asInt());
            transactions.setCurrencies(new CCurrencies(node.get("idCurrency").asInt()));
            transactions.setOperationTypes(COperationTypes.INGRESO);
            transactions.setTransactionsStatus(CTransactionsStatus.PAGADA);
            transactions.setTransactionNumber(node.get("transactionNumber").asText());
            transactions.setCreationDate(LocalDateTime.now());
            transactions.setIdAccessLevel(1);


            BigDecimal addAmountTransaction = balances.getCurrentAmount().add(transactions.getAmount());
            balances.setCurrentAmount(addAmountTransaction);
            balances.setModificationDate(LocalDateTime.now());
            balancesDao.update(balances);

            transactions.setBalances(balances);
            transactionsDao.save(transactions);

    }
}
