package mx.bidg.service.impl;

import mx.bidg.dao.CIncidenceDao;
import mx.bidg.model.CIncidence;
import mx.bidg.service.CIncidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@Transactional
@Service
public class CIncidenceServiceImpl implements CIncidenceService {

    @Autowired
    private CIncidenceDao cIncidenceDao;

    @Override
    public CIncidence findById(int id) {
        return cIncidenceDao.findById(id);
    }

    @Override
    public List<CIncidence> findAll() {
        return cIncidenceDao.findAll();
    }
}
