package mx.bidg.service;

import mx.bidg.model.DistributorsDetailBanksHistory;
import mx.bidg.model.Users;

import java.io.IOException;

/**
 * Created by Leonardo on 04/07/2017.
 */
public interface DistributorsDetailBanksHistoryService {
    DistributorsDetailBanksHistory saveData(String data, Users users, Integer idDistributor) throws IOException;
}
