package mx.bidg.service.impl;

import mx.bidg.dao.FixedOperationsDao;
import mx.bidg.model.FixedOperations;
import mx.bidg.service.FixedOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class FixedOperationsServiceImpl implements FixedOperationsService {

    @Autowired
    FixedOperationsDao fixedOperationsDao;

    @Override
    public FixedOperations save(FixedOperations fixedOperations) {
        return fixedOperationsDao.save(fixedOperations);
    }

    @Override
    public FixedOperations update(FixedOperations fixedOperations) {
        return fixedOperationsDao.update(fixedOperations);
    }

    @Override
    public FixedOperations findById(Integer idFixedOperations) {
        return fixedOperationsDao.findById(idFixedOperations);
    }

    @Override
    public boolean delete(FixedOperations fixedOperations) {
        fixedOperationsDao.delete(fixedOperations);
        return true;
    }

    @Override
    public List<FixedOperations> findAll() {
        return fixedOperationsDao.findAll();
    }
}
