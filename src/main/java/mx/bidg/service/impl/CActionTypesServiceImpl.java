package mx.bidg.service.impl;

import mx.bidg.dao.CActionTypesDao;
import mx.bidg.model.CActionTypes;
import mx.bidg.service.CActionTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 23/08/16.
 */
@Service
@Transactional
public class CActionTypesServiceImpl implements CActionTypesService {

    @Autowired
    CActionTypesDao cActionTypesDao;

    @Override
    public CActionTypes save(CActionTypes actionTypes) {
        return cActionTypesDao.save(actionTypes);
    }

    @Override
    public CActionTypes update(CActionTypes actionTypes) {
        return cActionTypesDao.update(actionTypes);
    }

    @Override
    public CActionTypes findById(Integer idActionType) {
        return cActionTypesDao.findById(idActionType);
    }

    @Override
    public List<CActionTypes> findAll() {
        return cActionTypesDao.findAll();
    }

    @Override
    public boolean delete(CActionTypes cActionTypes) {
        cActionTypesDao.delete(cActionTypes);
        return true;
    }
}
