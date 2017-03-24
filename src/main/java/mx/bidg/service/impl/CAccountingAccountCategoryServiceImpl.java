package mx.bidg.service.impl;

import mx.bidg.dao.CAccountingAccountCategoryDao;
import mx.bidg.model.CAccountingAccountCategory;
import mx.bidg.service.CAccountingAccountCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josue on 7/07/16.
 */
@Service
@Transactional
public class CAccountingAccountCategoryServiceImpl implements CAccountingAccountCategoryService {

    @Autowired
    CAccountingAccountCategoryDao cAccountingAccountCategoryDao;

    @Override
    public List<CAccountingAccountCategory> findAll() {
        return cAccountingAccountCategoryDao.findAll();
    }

    @Override
    public CAccountingAccountCategory findById(Integer idAccountingAccountCategory) {
        return cAccountingAccountCategoryDao.findById(idAccountingAccountCategory);
    }
}
