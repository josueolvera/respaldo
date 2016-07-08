package mx.bidg.service.impl;

import mx.bidg.dao.CAccountingAccountNatureDao;
import mx.bidg.model.CAccountingAccountNature;
import mx.bidg.service.CAccountingAccountNatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josue on 7/07/16.
 */
@Service
@Transactional
public class CAccountingAccountNatureServiceImpl implements CAccountingAccountNatureService {

    @Autowired
    CAccountingAccountNatureDao cAccountingAccountNatureDao;

    @Override
    public List<CAccountingAccountNature> findAll() {
        return cAccountingAccountNatureDao.findAll();
    }
}
