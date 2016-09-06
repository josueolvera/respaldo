package mx.bidg.service.impl;

import mx.bidg.dao.COperationsDao;
import mx.bidg.model.COperations;
import mx.bidg.service.COperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class COperationsServiceImpl implements COperationsService {

    @Autowired
    COperationsDao cOperationsDao;

    @Override
    public COperations save(COperations cOperations) {
        return cOperationsDao.save(cOperations);
    }

    @Override
    public COperations update(COperations cOperations) {
        return cOperationsDao.update(cOperations);
    }

    @Override
    public COperations findById(Integer idCOperations) {
        return cOperationsDao.findById(idCOperations);
    }

    @Override
    public boolean delete(COperations cOperations) {
        cOperationsDao.delete(cOperations);
        return true;
    }

    @Override
    public List<COperations> findAll() {
        return cOperationsDao.findAll();
    }
}
