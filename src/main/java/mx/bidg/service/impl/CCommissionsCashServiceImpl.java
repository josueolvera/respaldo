package mx.bidg.service.impl;

import mx.bidg.dao.CCommissionsCashDao;
import mx.bidg.model.CCommissionsCash;
import mx.bidg.service.CCommissionsCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC_YAIR on 15/11/2016.
 */
@Service
@Transactional
public class CCommissionsCashServiceImpl implements CCommissionsCashService {

    @Autowired
    CCommissionsCashDao cCommissionsCashDao;




    @Override
    public List<CCommissionsCash> findAll() {
        return cCommissionsCashDao.findAll();
    }

    @Override
    public CCommissionsCash update(CCommissionsCash cCommissionsCash) {
        return cCommissionsCashDao.update(cCommissionsCash);
    }

    @Override
    public CCommissionsCash findById(Integer idCommissionsCash) {
        return cCommissionsCashDao.findById(idCommissionsCash);
    }

    @Override
    public boolean delete(CCommissionsCash cCommissionsCash) {
        return cCommissionsCashDao.delete(cCommissionsCash);
    }
}
