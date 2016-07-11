package mx.bidg.service.impl;

import mx.bidg.dao.CAccountingAccountTypeDao;
import mx.bidg.model.CAccountingAccountType;
import mx.bidg.service.CAccountingAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josue on 7/07/16.
 */
@Service
@Transactional
public class CAccountingAccountTypeServiceImpl implements CAccountingAccountTypeService {

    @Autowired
    CAccountingAccountTypeDao cAccountingAccountTypeDao;

    @Override
    public List<CAccountingAccountType> findAll() {
        return cAccountingAccountTypeDao.findAll();
    }
}
