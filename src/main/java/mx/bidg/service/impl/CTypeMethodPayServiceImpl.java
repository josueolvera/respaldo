package mx.bidg.service.impl;

import mx.bidg.config.JsonViews;
import mx.bidg.dao.CTypeMethodPayDao;
import mx.bidg.model.CTypeMethodPay;
import mx.bidg.service.CTypeMethodPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
@Service
@Transactional
public class CTypeMethodPayServiceImpl implements CTypeMethodPayService {

    @Autowired
    private CTypeMethodPayDao cTypeMethodPayDao;

    @Override
    public CTypeMethodPay findById(Integer id) {
        return cTypeMethodPayDao.findById(id);
    }

    @Override
    public List<CTypeMethodPay> findAll() {
        return cTypeMethodPayDao.findAll();
    }

    @Override
    public CTypeMethodPay save(CTypeMethodPay cTypeMethodPay) {
        return cTypeMethodPayDao.save(cTypeMethodPay);
    }

    @Override
    public boolean delete(CTypeMethodPay cTypeMethodPay) {
        return cTypeMethodPayDao.delete(cTypeMethodPay);
    }

    @Override
    public CTypeMethodPay update(CTypeMethodPay cTypeMethodPay) {
        return cTypeMethodPayDao.update(cTypeMethodPay);
    }
}
