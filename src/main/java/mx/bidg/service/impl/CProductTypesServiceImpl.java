package mx.bidg.service.impl;

import mx.bidg.dao.CProductTypesDao;
import mx.bidg.model.CProductTypes;
import mx.bidg.service.CProductTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 *         Created on 19/11/15.
 */
@Service
@Transactional
public class CProductTypesServiceImpl implements CProductTypesService {

    @Autowired
    CProductTypesDao productTypesDao;

    @Override
    public CProductTypes findById(Integer id) {
        return productTypesDao.findById(id);
    }

    @Override
    public List<CProductTypes> findAll() {
        return productTypesDao.findAll();
    }
}
