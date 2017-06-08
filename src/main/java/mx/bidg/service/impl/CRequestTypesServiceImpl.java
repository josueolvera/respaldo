package mx.bidg.service.impl;

import mx.bidg.dao.CRequestTypesDao;
import mx.bidg.model.CRequestTypes;
import mx.bidg.service.CRequestTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desarrollador on 2/06/17.
 */
@Service
@Transactional
public class CRequestTypesServiceImpl implements CRequestTypesService {

    @Autowired
    CRequestTypesDao cRequestTypesDao;

    @Override
    public CRequestTypes findById(Integer idCRequestTypes) {
        return cRequestTypesDao.findById(idCRequestTypes);
    }

    @Override
    public CRequestTypes save(CRequestTypes cRequestTypes) {
        return cRequestTypesDao.save(cRequestTypes);
    }

    @Override
    public CRequestTypes update(CRequestTypes cRequestTypes) {
        return cRequestTypesDao.update(cRequestTypes);
    }

    @Override
    public List<CRequestTypes> findAll() {
        return cRequestTypesDao.findAll();
    }

    @Override
    public boolean delete(CRequestTypes cRequestTypes) {
        return cRequestTypesDao.delete(cRequestTypes);
    }
}
