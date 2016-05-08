package mx.bidg.service;

import mx.bidg.model.CSettlement;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface CSettlementService {
    List<CSettlement> findAll();
    CSettlement findById(Integer idSettlement);
    CSettlement save(CSettlement settlement);
    CSettlement update(CSettlement settlement);
    Boolean delete(CSettlement settlement);
}
