package mx.bidg.service.impl;

import mx.bidg.dao.CAmountsSecureDao;
import mx.bidg.model.CAmountsSecure;
import mx.bidg.service.CAmountsSecureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
@Service
@Transactional
public class CAmountsSecureServiceImpl implements CAmountsSecureService{
    @Autowired
    private CAmountsSecureDao cAmountsSecureDao;

    @Override
    public List<CAmountsSecure> findAll() {
        return cAmountsSecureDao.findAll();
    }

    @Override
    public CAmountsSecure findById(Integer id) {
        return cAmountsSecureDao.findById(id);
    }

    @Override
    public CAmountsSecure findByRode(BigDecimal rode) {
        return cAmountsSecureDao.findByRode(rode);
    }
}
