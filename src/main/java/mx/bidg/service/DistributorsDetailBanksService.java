package mx.bidg.service;

import mx.bidg.model.DistributorsDetailBanks;
import mx.bidg.model.DistributorsDetailBanksHistory;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Leonardo on 13/06/2017.
 */
public interface DistributorsDetailBanksService {
    List<DistributorsDetailBanks> findAll();
    DistributorsDetailBanks findById(Integer idDistributorDetailBank);
    DistributorsDetailBanks save(DistributorsDetailBanks distributorsDetailBanks);
    DistributorsDetailBanks update(DistributorsDetailBanks distributorsDetailBanks);
    boolean delete(DistributorsDetailBanks distributorsDetailBanks);
    List<DistributorsDetailBanks> getByDistributor(int id);
    DistributorsDetailBanks findByAccountNumber(String accountNumber);
    BigDecimal sumByDistributor(Integer idDistributor);
    DistributorsDetailBanks findLikeAccountNumber(String accountNumber);
    void exportFile(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException;
    //List<DistributorsDetailBanksHistory> findBetweenDates (LocalDateTime fromDate, LocalDateTime toDate);

}
