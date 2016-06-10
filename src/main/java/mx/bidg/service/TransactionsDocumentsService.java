package mx.bidg.service;

import mx.bidg.model.CTransactionsDocumentsTypes;
import mx.bidg.model.Transactions;
import mx.bidg.model.TransactionsDocuments;

import java.util.List;

/**
 * Created by jolvera on 10/06/16.
 */
public interface TransactionsDocumentsService {
    TransactionsDocuments save(TransactionsDocuments transactionsDocuments);
    TransactionsDocuments update(TransactionsDocuments transactionsDocuments);
    List<TransactionsDocuments> findAll();
    TransactionsDocuments findById(Integer idTransactionDocuments);
    boolean delete(TransactionsDocuments transactionsDocuments);
    List<TransactionsDocuments> findByIdTransaction(Integer idTransaction);
    TransactionsDocuments findBy(Transactions transactions, CTransactionsDocumentsTypes cTransactionsDocumentsTypes);
    List<TransactionsDocuments> findRecordBy(TransactionsDocuments transactionsDocuments);
}
