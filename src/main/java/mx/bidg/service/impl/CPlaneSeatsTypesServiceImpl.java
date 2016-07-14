package mx.bidg.service.impl;

import mx.bidg.dao.CPlaneSeatsTypesDao;
import mx.bidg.model.CPlaneSeatsTypes;
import mx.bidg.service.CPlaneSeatsTypesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public class CPlaneSeatsTypesServiceImpl implements CPlaneSeatsTypesService {

    @Autowired
    private CPlaneSeatsTypesDao cPlaneSeatsTypesDao;

    @Override
    public List<CPlaneSeatsTypes> findAll() {
        return cPlaneSeatsTypesDao.findAll();
    }

    @Override
    public CPlaneSeatsTypes findById(Integer id) {
        return cPlaneSeatsTypesDao.findById(id);
    }

    @Override
    public CPlaneSeatsTypes save(CPlaneSeatsTypes planeSeatType) {
        return cPlaneSeatsTypesDao.save(planeSeatType);
    }

    @Override
    public CPlaneSeatsTypes update(CPlaneSeatsTypes planeSeatType) {
        return cPlaneSeatsTypesDao.update(planeSeatType);
    }

    @Override
    public Boolean delete(CPlaneSeatsTypes planeSeatType) {
        return cPlaneSeatsTypesDao.delete(planeSeatType);
    }
}
