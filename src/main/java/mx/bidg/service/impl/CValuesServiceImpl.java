package mx.bidg.service.impl;

import mx.bidg.dao.CValuesDao;
import mx.bidg.model.CValues;
import mx.bidg.service.CValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
@Service
@Transactional
public class CValuesServiceImpl implements CValuesService {

    @Autowired
    private CValuesDao valuesDao;

    @Override
    public List<CValues> findAll() {
        return valuesDao.findAll();
    }
}
