package mx.bidg.service.impl;

import mx.bidg.dao.FlightsDao;
import mx.bidg.model.Flights;
import mx.bidg.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@Service
@Transactional
public class FlightsServiceImpl implements FlightsService {

    @Autowired
    private FlightsDao flightsDao;

    @Override
    public List<Flights> findAll() {
        return flightsDao.findAll();
    }

    @Override
    public Flights findById(Integer id) {
        return flightsDao.findById(id);
    }

    @Override
    public Flights save(Flights flight) {
        return flightsDao.save(flight);
    }

    @Override
    public Flights update(Flights flight) {
        return flightsDao.update(flight);
    }

    @Override
    public Boolean delete(Flights flight) {
        return flightsDao.delete(flight);
    }
}
