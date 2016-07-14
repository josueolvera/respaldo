package mx.bidg.service.impl;

import mx.bidg.dao.CTravelExpensesConceptsDao;
import mx.bidg.model.CTravelExpensesConcepts;
import mx.bidg.service.CTravelExpensesConceptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class CTravelExpensesConceptsServiceImpl implements CTravelExpensesConceptsService {

    @Autowired
    CTravelExpensesConceptsDao cTravelExpensesConceptsDao;

    @Override
    public CTravelExpensesConcepts save(CTravelExpensesConcepts cTravelExpensesConcepts) {
        cTravelExpensesConceptsDao.save(cTravelExpensesConcepts);
        return cTravelExpensesConcepts;
    }

    @Override
    public CTravelExpensesConcepts update(CTravelExpensesConcepts cTravelExpensesConcepts) {
        cTravelExpensesConceptsDao.update(cTravelExpensesConcepts);
        return cTravelExpensesConcepts;
    }

    @Override
    public CTravelExpensesConcepts findById(Integer idTravelExpensesConcepts) {
        return cTravelExpensesConceptsDao.findById(idTravelExpensesConcepts);
    }

    @Override
    public List<CTravelExpensesConcepts> findAll() {
        return cTravelExpensesConceptsDao.findAll();
    }

    @Override
    public Boolean delete(CTravelExpensesConcepts cTravelExpensesConcepts) {
        cTravelExpensesConceptsDao.delete(cTravelExpensesConcepts);
        return true;
    }
}
