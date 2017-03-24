package mx.bidg.service.impl;

import mx.bidg.dao.CConceptBudgetDao;
import mx.bidg.model.CConceptBudget;
import mx.bidg.service.CConceptBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 07/03/2017.
 */
@Service
@Transactional
public class CConceptBudgetServiceImpl implements CConceptBudgetService {

    @Autowired
    private CConceptBudgetDao cConceptBudgetDao;

    @Override
    public List<CConceptBudget> findAll() {
        return cConceptBudgetDao.findAll();
    }

    @Override
    public CConceptBudget save(CConceptBudget cConceptBudget) {
        return cConceptBudgetDao.save(cConceptBudget);
    }

    @Override
    public CConceptBudget update(CConceptBudget cConceptBudget) {
        return cConceptBudgetDao.update(cConceptBudget);
    }

    @Override
    public boolean delete(CConceptBudget cConceptBudget) {
        return cConceptBudgetDao.delete(cConceptBudget);
    }

    @Override
    public CConceptBudget findById(Integer id) {
        return cConceptBudgetDao.findById(id);
    }
}
