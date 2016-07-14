package mx.bidg.service.impl;

import mx.bidg.dao.CPlaneTicketsTypesDao;
import mx.bidg.model.CPlaneTicketsTypes;
import mx.bidg.service.CPlaneTicketsTypesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public class CPlaneTicketsTypesServiceImpl implements CPlaneTicketsTypesService {

    @Autowired
    private CPlaneTicketsTypesDao cPlaneTicketsTypesDao;

    @Override
    public List<CPlaneTicketsTypes> findAll() {
        return cPlaneTicketsTypesDao.findAll();
    }

    @Override
    public CPlaneTicketsTypes findById(Integer id) {
        return cPlaneTicketsTypesDao.findById(id);
    }

    @Override
    public CPlaneTicketsTypes save(CPlaneTicketsTypes planeTicketType) {
        return cPlaneTicketsTypesDao.save(planeTicketType);
    }

    @Override
    public CPlaneTicketsTypes update(CPlaneTicketsTypes planeTicketType) {
        return cPlaneTicketsTypesDao.update(planeTicketType);
    }

    @Override
    public Boolean delete(CPlaneTicketsTypes planeTicketType) {
        return cPlaneTicketsTypesDao.delete(planeTicketType);
    }
}
