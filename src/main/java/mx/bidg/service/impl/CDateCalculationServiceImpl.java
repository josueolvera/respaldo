package mx.bidg.service.impl;

import mx.bidg.dao.CDateCalculationDao;
import mx.bidg.model.CDateCalculation;
import mx.bidg.service.CDateCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 6/10/16.
 */
@Service
@Transactional
public class CDateCalculationServiceImpl implements CDateCalculationService {

    @Autowired
    CDateCalculationDao cDateCalculationDao;

    @Override
    public CDateCalculation save(CDateCalculation cDateCalculation) {
        return cDateCalculationDao.save(cDateCalculation);
    }

    @Override
    public CDateCalculation update(CDateCalculation cDateCalculation) {
        return cDateCalculationDao.update(cDateCalculation);
    }

    @Override
    public CDateCalculation findById(Integer idDateCalculation) {
        return cDateCalculationDao.findById(idDateCalculation);
    }

    @Override
    public List<CDateCalculation> findAll() {
        return cDateCalculationDao.findAll();
    }

    @Override
    public boolean delete(CDateCalculation cDateCalculation) {
        cDateCalculationDao.delete(cDateCalculation);
        return true;
    }

    @Override
    public List<CDateCalculation> findByStatus(Integer status) {
        return cDateCalculationDao.findByStatus(status);
    }
}
