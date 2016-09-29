package mx.bidg.service.impl;

import mx.bidg.dao.RequestConceptDao;
import mx.bidg.model.RequestConcept;
import mx.bidg.service.RequestConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Service
@Transactional
public class RequestConceptServiceImpl implements RequestConceptService {

    @Autowired
    RequestConceptDao requestConceptDao;

    @Override
    public RequestConcept save(RequestConcept requestConcept) {
        requestConceptDao.save(requestConcept);
        return requestConcept;
    }

    @Override
    public RequestConcept update(RequestConcept requestConcept) {
        requestConceptDao.update(requestConcept);
        return requestConcept;
    }

    @Override
    public RequestConcept findById(Integer idRequestConcept) {
        return requestConceptDao.findById(idRequestConcept);
    }

    @Override
    public List<RequestConcept> findAll() {
        return requestConceptDao.findAll();
    }

    @Override
    public Boolean delete(RequestConcept requestConcept) {
        requestConceptDao.delete(requestConcept);
        return true;
    }
}
