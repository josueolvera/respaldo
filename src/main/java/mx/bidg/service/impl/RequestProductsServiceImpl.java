package mx.bidg.service.impl;

import mx.bidg.dao.RequestProductsDao;
import mx.bidg.model.RequestProducts;
import mx.bidg.service.RequestProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desarrollador on 30/05/17.
 */
@Service
@Transactional
public class RequestProductsServiceImpl implements RequestProductsService {

    @Autowired
    RequestProductsDao requestProductsDao;

    @Override
    public RequestProducts save(RequestProducts requestProduct) {
        return requestProductsDao.save(requestProduct);
    }

    @Override
    public RequestProducts update(RequestProducts requestProduct) {
        return requestProductsDao.update(requestProduct);
    }

    @Override
    public RequestProducts findById(Integer idRequestProduct) {
        return requestProductsDao.findById(idRequestProduct);
    }

    @Override
    public boolean delete(RequestProducts requestProduct) {
        return requestProductsDao.delete(requestProduct);
    }

    @Override
    public List<RequestProducts> findAll() {
        return requestProductsDao.findAll();
    }

    @Override
    public List<RequestProducts> findByIdRequest(Integer idRequest) {
        return requestProductsDao.findByIdRequest(idRequest);
    }
}
