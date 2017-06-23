package mx.bidg.service;

import mx.bidg.model.PayRequestsHistory;
import mx.bidg.model.Users;

import java.io.IOException;

/**
 * Created by User on 21/06/2017.
 */
public interface PayRequestsHistoryService {
    PayRequestsHistory saveData(String data, Users user) throws IOException;
}
