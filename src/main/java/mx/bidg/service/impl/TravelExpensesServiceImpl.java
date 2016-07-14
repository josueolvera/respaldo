package mx.bidg.service.impl;

import mx.bidg.dao.TravelExpensesDao;
import mx.bidg.model.TravelExpenses;
import mx.bidg.service.TravelExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class TravelExpensesServiceImpl implements TravelExpensesService {

    @Autowired
    TravelExpensesDao travelExpensesDao;

    @Override
    public TravelExpenses save(TravelExpenses travelExpenses) {
        travelExpensesDao.save(travelExpenses);
        return travelExpenses;
    }

    @Override
    public TravelExpenses update(TravelExpenses travelExpenses) {
        travelExpensesDao.update(travelExpenses);
        return travelExpenses;
    }

    @Override
    public TravelExpenses findById(Integer idTravelExpenses) {
        return travelExpensesDao.findById(idTravelExpenses);
    }

    @Override
    public List<TravelExpenses> findAll() {
        return travelExpensesDao.findAll();
    }

    @Override
    public Boolean delete(TravelExpenses travelExpenses) {
        travelExpensesDao.delete(travelExpenses);
        return true;
    }
}
