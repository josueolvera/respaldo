package mx.bidg.service.impl;

import mx.bidg.dao.CEmployeeDocumentsTypesDao;
import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.service.CEmployeeDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
@Service
@Transactional
public class CEmployeeDocumentsTypesServiceImpl implements CEmployeeDocumentsTypesService {

    @Autowired
    CEmployeeDocumentsTypesDao cEmployeeDocumentsTypesDao;

    @Override
    public List<CEmployeeDocumentsTypes> findAll() {
        return cEmployeeDocumentsTypesDao.findAll();
    }

}
