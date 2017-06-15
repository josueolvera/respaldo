package mx.bidg.service;


import mx.bidg.model.CAccountBanksType;

import java.util.List;


/**
 * Created by Leonardo on 12/06/2017.
 */
public interface CAccountBanksTypeService {

    List<CAccountBanksType> findAll();
    CAccountBanksType findById (Integer idAccountBankType);
    CAccountBanksType save (CAccountBanksType cAccountBanksType);
    CAccountBanksType update (CAccountBanksType cAccountBanksType);
    boolean delete(CAccountBanksType cAccountBanksType);
}
