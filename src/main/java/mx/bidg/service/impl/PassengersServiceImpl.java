package mx.bidg.service.impl;

import mx.bidg.dao.PassengersDao;
import mx.bidg.model.Passengers;
import mx.bidg.service.PassengersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@Service
@Transactional
public class PassengersServiceImpl implements PassengersService {

    @Autowired
    private PassengersDao passengersDao;

    @Override
    public List<Passengers> findAll() {
        return passengersDao.findAll();
    }

    @Override
    public Passengers findById(Integer id) {
        return passengersDao.findById(id);
    }

    @Override
    public Passengers save(Passengers passenger) {
        return passengersDao.save(passenger);
    }

    @Override
    public Passengers update(Passengers passenger) {
        return passengersDao.update(passenger);
    }

    @Override
    public Boolean delete(Passengers passenger) {
        return passengersDao.delete(passenger);
    }
}
