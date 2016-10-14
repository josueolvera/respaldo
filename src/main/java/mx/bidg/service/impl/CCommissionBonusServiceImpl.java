package mx.bidg.service.impl;

import mx.bidg.dao.CCommissionBonusDao;
import mx.bidg.model.CCommissionBonus;
import mx.bidg.service.CCommissionBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josue on 11/10/2016.
 */
@Service
@Transactional
public class CCommissionBonusServiceImpl implements CCommissionBonusService {

    @Autowired
    CCommissionBonusDao cCommissionBonusDao;

    @Override
    public CCommissionBonus save(CCommissionBonus cCommissionBonus) {
        return cCommissionBonusDao.save(cCommissionBonus);
    }

    @Override
    public CCommissionBonus update(CCommissionBonus cCommissionBonus) {
        return cCommissionBonusDao.update(cCommissionBonus);
    }

    @Override
    public CCommissionBonus findById(Integer idCommissionBonus) {
        return cCommissionBonusDao.findById(idCommissionBonus);
    }

    @Override
    public boolean delete(CCommissionBonus cCommissionBonus) {
        cCommissionBonusDao.delete(cCommissionBonus);
        return true;
    }

    @Override
    public List<CCommissionBonus> findAll() {
        return cCommissionBonusDao.findAll();
    }
}
