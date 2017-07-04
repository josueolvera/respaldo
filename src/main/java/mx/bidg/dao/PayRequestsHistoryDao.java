package mx.bidg.dao;

import mx.bidg.model.PayRequestsHistory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by User on 21/06/2017.
 */
public interface PayRequestsHistoryDao extends InterfaceDao<PayRequestsHistory>{
    List<PayRequestsHistory> findBetweenDates (LocalDateTime fromDate, LocalDateTime toDate);
}
