package mx.bidg.service.impl;

import mx.bidg.dao.CRequestTypesDao;
import mx.bidg.model.CRequestTypes;
import mx.bidg.service.CRequestTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 18/11/15.
 */
@Service
@Transactional
public class CRequestTypesServiceImpl implements CRequestTypesService {

    @Autowired
    CRequestTypesDao requestTypesDao;

    @Override
    public List<CRequestTypes> findAll() {
        return requestTypesDao.findAll();
    }
}
