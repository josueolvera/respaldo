package mx.bidg.service.impl;

import mx.bidg.dao.RulesCalculationDao;
import mx.bidg.model.RulesCalculation;
import mx.bidg.service.RulesCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class RulesCalculationServiceImpl implements RulesCalculationService {

    @Autowired
    RulesCalculationDao rulesCalculationDao;

    @Override
    public RulesCalculation save(RulesCalculation rulesCalculation) {
        return rulesCalculationDao.save(rulesCalculation);
    }

    @Override
    public RulesCalculation update(RulesCalculation rulesCalculation) {
        return rulesCalculationDao.update(rulesCalculation);
    }

    @Override
    public RulesCalculation findById(Integer idRulesCalculation) {
        return rulesCalculationDao.findById(idRulesCalculation);
    }

    @Override
    public boolean delete(RulesCalculation rulesCalculation) {
        rulesCalculationDao.delete(rulesCalculation);
        return true;
    }

    @Override
    public List<RulesCalculation> findAll() {
        return rulesCalculationDao.findAll();
    }
}
