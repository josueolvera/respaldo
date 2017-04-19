package mx.bidg.service.impl;

import mx.bidg.dao.CSubareasDao;
import mx.bidg.model.CSubareas;
import mx.bidg.service.CSubareasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by josue on 19/04/2017.
 */
@Service
@Transactional
public class CSubareasServiceImpl implements CSubareasService {

    @Autowired
    CSubareasDao cSubareasDao;

    @Override
    public CSubareas save(CSubareas subareas) {
        return cSubareasDao.save(subareas);
    }

    @Override
    public CSubareas update(CSubareas subareas) {
        return cSubareasDao.update(subareas);
    }

    @Override
    public CSubareas findById(Integer idCSubareas) {
        return cSubareasDao.findById(idCSubareas);
    }

    @Override
    public List<CSubareas> findAll() {
        return cSubareasDao.findAll();
    }

    @Override
    public boolean delete(CSubareas subareas) {
        return cSubareasDao.delete(subareas);
    }
}
