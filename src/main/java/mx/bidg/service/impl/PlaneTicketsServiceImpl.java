package mx.bidg.service.impl;

import mx.bidg.dao.PlaneTicketsDao;
import mx.bidg.model.PlaneTickets;
import mx.bidg.service.PlaneTicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@Service
@Transactional
public class PlaneTicketsServiceImpl implements PlaneTicketsService {

    @Autowired
    private PlaneTicketsDao planeTicketsDao;

    @Override
    public List<PlaneTickets> findAll() {
        return planeTicketsDao.findAll();
    }

    @Override
    public PlaneTickets findById(Integer id) {
        return planeTicketsDao.findById(id);
    }

    @Override
    public PlaneTickets save(PlaneTickets planeTicket) {
        return planeTicketsDao.save(planeTicket);
    }

    @Override
    public PlaneTickets update(PlaneTickets planeTicket) {
        return planeTicketsDao.update(planeTicket);
    }

    @Override
    public Boolean delete(PlaneTickets planeTicket) {
        return planeTicketsDao.delete(planeTicket);
    }
}
