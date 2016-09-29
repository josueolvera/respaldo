package mx.bidg.service.impl;

import mx.bidg.dao.TravelExpenseConceptDao;
import mx.bidg.model.TravelExpenseConcept;
import mx.bidg.service.TravelExpenseConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class TravelExpenseConceptServiceImpl implements TravelExpenseConceptService {

    @Autowired
    TravelExpenseConceptDao travelExpenseConceptDao;

    @Override
    public TravelExpenseConcept save(TravelExpenseConcept travelExpenseConcept) {
        travelExpenseConceptDao.save(travelExpenseConcept);
        return travelExpenseConcept;
    }

    @Override
    public TravelExpenseConcept update(TravelExpenseConcept travelExpenseConcept) {
        travelExpenseConceptDao.update(travelExpenseConcept);
        return travelExpenseConcept;
    }

    @Override
    public TravelExpenseConcept findById(Integer idRequestConcept) {
        return travelExpenseConceptDao.findById(idRequestConcept);
    }

    @Override
    public List<TravelExpenseConcept> findAll() {
        return travelExpenseConceptDao.findAll();
    }

    @Override
    public Boolean delete(TravelExpenseConcept travelExpenseConcept) {
        travelExpenseConceptDao.delete(travelExpenseConcept);
        return true;
    }
}
