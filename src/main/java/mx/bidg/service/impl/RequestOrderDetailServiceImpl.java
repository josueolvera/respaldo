package mx.bidg.service.impl;

import mx.bidg.dao.RequestOrderDetailDao;
import mx.bidg.model.RequestOrderDetail;
import mx.bidg.service.RequestOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desarrollador on 11/06/17.
 */
@Service
@Transactional
public class RequestOrderDetailServiceImpl implements RequestOrderDetailService {

    @Autowired
    private RequestOrderDetailDao requestOrderDetailDao;

    @Override
    public RequestOrderDetail save(RequestOrderDetail requestOrderDetail) {
        return requestOrderDetailDao.save(requestOrderDetail);
    }

    @Override
    public RequestOrderDetail update(RequestOrderDetail requestOrderDetail) {
        return requestOrderDetailDao.update(requestOrderDetail);
    }

    @Override
    public RequestOrderDetail findById(Integer idRequestOrderDetail) {
        return requestOrderDetailDao.findById(idRequestOrderDetail);
    }

    @Override
    public List<RequestOrderDetail> findAll() {
        return requestOrderDetailDao.findAll();
    }

    @Override
    public boolean delete(RequestOrderDetail requestOrderDetail) {
        return requestOrderDetailDao.delete(requestOrderDetail);
    }

    @Override
    public List<RequestOrderDetail> findByidReqOrderDocument(Integer idRequestOrderDocument) {
        return requestOrderDetailDao.findByidReqOrderDocument(idRequestOrderDocument);
    }
}
