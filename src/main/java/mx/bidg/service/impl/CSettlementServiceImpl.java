package mx.bidg.service.impl;

import mx.bidg.dao.CSettlementDao;
import mx.bidg.model.CSettlement;
import mx.bidg.service.CSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Service
@Transactional
public class CSettlementServiceImpl implements CSettlementService {

    @Autowired
    CSettlementDao dao;

    @Override
    public List<CSettlement> findAll() {
        return dao.findAll();
    }

    @Override
    public CSettlement findById(Integer idSettlement) {
        return dao.findById(idSettlement);
    }

    @Override
    public CSettlement save(CSettlement settlement) {
        dao.save(settlement);
        return settlement;
    }

    @Override
    public CSettlement update(CSettlement settlement) {
        dao.update(settlement);
        return settlement;
    }

    @Override
    public Boolean delete(CSettlement settlement) {
        dao.delete(settlement);
        return true;
    }
}
