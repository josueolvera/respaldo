package mx.bidg.service.impl;

import mx.bidg.dao.RequestsDatesDao;
import mx.bidg.model.RequestsDates;
import mx.bidg.service.RequestsDatesService;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jcesar on 12/06/2017.
 */
@Service
@Transactional
public class RequestsDatesServiceImpl implements RequestsDatesService {
    @Autowired
    RequestsDatesDao requestsDatesDao;

    @Override
    public RequestsDates save(RequestsDates requestsDates) {
        return save(requestsDatesDao.save(requestsDates));
    }

    @Override
    public RequestsDates update(RequestsDates requestsDates) {
        return update(requestsDatesDao.update(requestsDates));
    }

    @Override
    public RequestsDates findById(Integer idRequestsDates) {
        return requestsDatesDao.findById(idRequestsDates);
    }

    @Override
    public List<RequestsDates> findAll() {
        return requestsDatesDao.findAll();
    }

    @Override
    public boolean delete(RequestsDates requestsDates) {
        return requestsDatesDao.delete(requestsDates);
    }

    @Override
    public RequestsDates getByRequest(Integer idRequest){
       return requestsDatesDao.getByRequest(idRequest);
    }
}
