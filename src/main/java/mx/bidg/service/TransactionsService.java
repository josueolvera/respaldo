package mx.bidg.service;

import mx.bidg.model.Transactions;
import mx.bidg.model.Users;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
public interface TransactionsService {
    Transactions findById(Integer id);
    Transactions save(Transactions transaction);
    Transactions update(Transactions transaction);
    boolean delete(Transactions transaction);
    List<Transactions> findAll();
    void entryPayAccount (String data , Users user) throws IOException;
    void transacctionsByDayReport(LocalDateTime ofdate, LocalDateTime untilDate , OutputStream stream) throws IOException;
    void transacctionsByDayReportAndExit(LocalDateTime ofdate, LocalDateTime untilDate , OutputStream stream) throws IOException;
}
