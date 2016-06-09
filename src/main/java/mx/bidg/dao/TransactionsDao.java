package mx.bidg.dao;

import mx.bidg.model.AccountsPayable;
import mx.bidg.model.Transactions;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
public interface TransactionsDao extends InterfaceDao<Transactions> {
    Transactions findByAccount (AccountsPayable accountsPayable);
    List<Transactions> findTransactionByDate(LocalDateTime ofDate, LocalDateTime untilDate);
    List<Transactions> findTransactionByDateAndExit(LocalDateTime ofDate, LocalDateTime untilDate);
}
