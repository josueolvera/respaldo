package mx.bidg.service.impl;

import mx.bidg.dao.CEmployeeTypeDao;
import mx.bidg.model.CEmployeeType;
import mx.bidg.service.CEmployeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
@Service
@Transactional
public class CEmployeeTypeServiceImpl implements CEmployeeTypeService {

    @Autowired
    CEmployeeTypeDao cEmployeeTypeDao;

    @Override
    public CEmployeeType findById(Integer idEmployeeType) {
        return cEmployeeTypeDao.findById(idEmployeeType);
    }

    @Override
    public List<CEmployeeType> findAll() {
        return cEmployeeTypeDao.findAll();
    }
}
