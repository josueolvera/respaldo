package mx.bidg.service;

import mx.bidg.model.DistributorsDetailBanks;

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
}
