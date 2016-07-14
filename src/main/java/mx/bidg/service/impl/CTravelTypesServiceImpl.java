package mx.bidg.service.impl;

import mx.bidg.dao.CTravelTypesDao;
import mx.bidg.model.CTravelTypes;
import mx.bidg.service.CTravelTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class CTravelTypesServiceImpl implements CTravelTypesService {

    @Autowired
    CTravelTypesDao cTravelTypesDao;

    @Override
    public CTravelTypes save(CTravelTypes cTravelTypes) {
        cTravelTypesDao.save(cTravelTypes);
        return cTravelTypes;
    }

    @Override
    public CTravelTypes update(CTravelTypes cTravelTypes) {
        cTravelTypesDao.update(cTravelTypes);
        return cTravelTypes;
    }

    @Override
    public CTravelTypes findById(Integer idTravelType) {
        return cTravelTypesDao.findById(idTravelType);
    }

    @Override
    public List<CTravelTypes> findAll() {
        return cTravelTypesDao.findAll();
    }

    @Override
    public Boolean delete(CTravelTypes cTravelTypes) {
        cTravelTypesDao.delete(cTravelTypes);
        return true;
    }
}
