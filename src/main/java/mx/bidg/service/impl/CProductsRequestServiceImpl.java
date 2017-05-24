package mx.bidg.service.impl;

import mx.bidg.dao.CProductsRequestDao;
import mx.bidg.model.CProductsRequest;
import mx.bidg.service.CProductsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
@Service
@Transactional
public class CProductsRequestServiceImpl implements CProductsRequestService{

    @Autowired
    private CProductsRequestDao cProductsRequestDao;

    @Override
    public List<CProductsRequest> findAll() {
        return cProductsRequestDao.findAll();
    }

    @Override
    public CProductsRequest findById(int id){
        return cProductsRequestDao.findById(id);
    }
}
