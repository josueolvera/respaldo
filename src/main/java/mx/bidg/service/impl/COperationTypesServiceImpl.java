package mx.bidg.service.impl;

import mx.bidg.dao.COperationTypesDao;
import mx.bidg.model.COperationTypes;
import mx.bidg.service.COperationTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Service
@Transactional
public class COperationTypesServiceImpl implements COperationTypesService {

    @Autowired
    COperationTypesDao cOperationTypesDao;

    @Override
    public List<COperationTypes> findAll() {
        return cOperationTypesDao.findAll();
    }
}
