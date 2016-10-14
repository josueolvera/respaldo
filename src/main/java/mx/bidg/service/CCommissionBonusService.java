package mx.bidg.service;

import mx.bidg.model.CCommissionBonus;

import java.util.List;

/**
 * Created by josue on 11/10/2016.
 */
public interface CCommissionBonusService {
    CCommissionBonus save(CCommissionBonus cCommissionBonus);
    CCommissionBonus update(CCommissionBonus cCommissionBonus);
    CCommissionBonus findById(Integer idCommissionBonus);
    boolean delete(CCommissionBonus cCommissionBonus);
    List<CCommissionBonus> findAll();
}
