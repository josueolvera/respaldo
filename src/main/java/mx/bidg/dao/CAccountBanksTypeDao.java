package mx.bidg.dao;

import mx.bidg.model.CAccountBanksType;

import java.util.List;

/**
 * Created by Leonardo on 12/06/2017.
 */
public interface CAccountBanksTypeDao extends InterfaceDao<CAccountBanksType>{
    List<CAccountBanksType> findAll();
}
