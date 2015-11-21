package mx.bidg.service.impl;

import mx.bidg.dao.CProductsDao;
import mx.bidg.model.CProducts;
import mx.bidg.service.CProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
@Service
public class CProductsServiceImpl implements CProductsService {

    @Autowired
    CProductsDao productsDao;

    @Override
    public CProducts findById(int id) {
        return productsDao.findById(id);
    }

    @Override
    public List<CProducts> findAll() {
        return productsDao.findAll();
    }
}
