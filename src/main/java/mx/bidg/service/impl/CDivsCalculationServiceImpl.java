package mx.bidg.service.impl;

import mx.bidg.dao.CDivsCalculationDao;
import mx.bidg.model.CDivsCalculation;
import mx.bidg.service.CDivsCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class CDivsCalculationServiceImpl implements CDivsCalculationService {

    @Autowired
    CDivsCalculationDao cDivsCalculationDao;

    @Override
    public CDivsCalculation save(CDivsCalculation cDivsCalculation) {
        return cDivsCalculationDao.save(cDivsCalculation);
    }

    @Override
    public CDivsCalculation update(CDivsCalculation cDivsCalculation) {
        return cDivsCalculationDao.update(cDivsCalculation);
    }

    @Override
    public CDivsCalculation findById(Integer idCDivsCalculation) {
        return cDivsCalculationDao.findById(idCDivsCalculation);
    }

    @Override
    public boolean delete(CDivsCalculation cDivsCalculation) {
        cDivsCalculationDao.delete(cDivsCalculation);
        return true;
    }

    @Override
    public List<CDivsCalculation> findAll() {
        return cDivsCalculationDao.findAll();
    }
}
