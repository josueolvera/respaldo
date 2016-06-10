package mx.bidg.service.impl;

import mx.bidg.dao.TransactionsDocumentsDao;
import mx.bidg.model.CTransactionsDocumentsTypes;
import mx.bidg.model.Transactions;
import mx.bidg.model.TransactionsDocuments;
import mx.bidg.service.TransactionsDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jolvera on 10/06/16.
 */
@Service
public class TransactionsDocumentsServiceImpl implements TransactionsDocumentsService {

    @Autowired
    TransactionsDocumentsDao transactionsDocumentsDao;

    @Override
    public TransactionsDocuments save(TransactionsDocuments transactionsDocuments) {
        transactionsDocumentsDao.save(transactionsDocuments);
        return transactionsDocuments;
    }

    @Override
    public TransactionsDocuments update(TransactionsDocuments transactionsDocuments) {
        transactionsDocumentsDao.update(transactionsDocuments);
        return transactionsDocuments;
    }

    @Override
    public List<TransactionsDocuments> findAll() {
        return transactionsDocumentsDao.findAll();
    }

    @Override
    public TransactionsDocuments findById(Integer idTransactionsDocuments) {
        return transactionsDocumentsDao.findById(idTransactionsDocuments);
    }

    @Override
    public boolean delete(TransactionsDocuments transactionsDocuments) {
        transactionsDocumentsDao.delete(transactionsDocuments);
        return true;
    }

    @Override
    public List<TransactionsDocuments> findByIdAccountPayable(Integer idTransactions) {
        return transactionsDocumentsDao.findByIdAccountPayable(idTransactions);
    }

    @Override
    public TransactionsDocuments findBy(Transactions transactions, CTransactionsDocumentsTypes cTransactionsDocumentsTypes) {
        return transactionsDocumentsDao.findBy(transactions,cTransactionsDocumentsTypes);
    }

    @Override
    public List<TransactionsDocuments> findRecordBy(TransactionsDocuments transactionsDocuments) {
        return findRecordBy(transactionsDocuments);
    }
}
