package mx.bidg.dao.impl;

import mx.bidg.dao.CSizesDao;
import mx.bidg.model.CSizes;
import mx.bidg.service.CSizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 01/08/16.
 */
@Transactional
@Service
public class CSizesServiceImpl implements CSizesService {

    @Autowired
    private CSizesDao cSizesDao;

    @Override
    public List<CSizes> findAll() {
        return cSizesDao.findAll();
    }

    @Override
    public CSizes findById(Integer idSize) {
        return cSizesDao.findById(idSize);
    }

    @Override
    public CSizes save(CSizes size) {
        return cSizesDao.save(size);
    }

    @Override
    public CSizes update(CSizes size) {
        return cSizesDao.update(size);
    }

    @Override
    public Boolean delete(CSizes size) {
        return cSizesDao.delete(size);
    }
}
