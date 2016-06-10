package mx.bidg.dao;

import mx.bidg.model.CTransactionsDocumentsTypes;
import mx.bidg.model.Transactions;
import mx.bidg.model.TransactionsDocuments;

import java.util.List;

/**
 * Created by jolvera on 9/06/16.
 */
public interface TransactionsDocumentsDao extends InterfaceDao <TransactionsDocuments> {
    List<TransactionsDocuments> findByIdAccountPayable (Integer idTransaction);
    TransactionsDocuments findBy (Transactions transactions, CTransactionsDocumentsTypes cTransactionsDocumentsType);
    List<TransactionsDocuments> findRecordBy (Transactions transactions);
}
