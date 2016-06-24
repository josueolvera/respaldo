package mx.bidg.service.impl;

import mx.bidg.dao.CStatusMaritalDao;
import mx.bidg.model.CStatusMarital;
import mx.bidg.service.CStatusMaritalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 23/06/16.
 */
@Service
@Transactional
public class CStatusMaritalServiceImpl implements CStatusMaritalService {

    @Autowired
    CStatusMaritalDao cStatusMaritalDao;

    @Override
    public List<CStatusMarital> findAll() {
        return cStatusMaritalDao.findAll();
    }
}
