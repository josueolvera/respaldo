package mx.bidg.service;

import mx.bidg.model.PayRequestsHistory;
import mx.bidg.model.Users;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by User on 21/06/2017.
 */
public interface PayRequestsHistoryService {
    List<PayRequestsHistory> findAll();
    List<PayRequestsHistory> saveData(String data, Users user) throws IOException;
    void payRequestsReport(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException;
    List<PayRequestsHistory> findBetweenDates (LocalDateTime fromDate, LocalDateTime toDate);
}
