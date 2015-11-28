package mx.bidg.service.impl;

import mx.bidg.dao.impl.CViewsDaoImpl;
import mx.bidg.model.CViews;
import mx.bidg.service.CViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/11/15.
 */
@Service
@Transactional
public class CViewsServiceImpl implements CViewsService {

    @Autowired
    CViewsDaoImpl viewsDao;

    @Override
    public List<CViews> findAll() {
        return viewsDao.findAll();
    }

    @Override
    public CViews findById(int id) {
        return viewsDao.findById(id);
    }
}
