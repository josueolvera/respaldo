package mx.bidg.service.impl;

import mx.bidg.dao.CBudgetNatureDao;
import mx.bidg.model.CBudgetNature;
import mx.bidg.service.CBudgetNatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 03/04/2017.
 */
@Service
@Transactional
public class CBudgetNatureServiceImpl implements CBudgetNatureService{

    @Autowired
    private CBudgetNatureDao cBudgetNatureDao;

    @Override
    public List<CBudgetNature> findAll() {
        return cBudgetNatureDao.findAll();
    }

    @Override
    public CBudgetNature findById(Integer id) {
        return cBudgetNatureDao.findById(id);
    }
}
