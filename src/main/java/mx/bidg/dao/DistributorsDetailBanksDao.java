package mx.bidg.dao;

import mx.bidg.model.DistributorsDetailBanks;

import java.util.List;


/**
 * Created by Leonardo on 13/06/2017.
 */
public interface DistributorsDetailBanksDao extends InterfaceDao<DistributorsDetailBanks> {
    List<DistributorsDetailBanks> findAll();
    List<DistributorsDetailBanks> getByDistributor(int id);
    DistributorsDetailBanks findByAccountNumber(String accountNumber);
}
