package mx.bidg.service.impl;

import mx.bidg.dao.CBudgetSubSubcategoriesDao;
import mx.bidg.model.CBudgetSubSubcategories;
import mx.bidg.service.CBudgetSubSubcategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/02/2017.
 */
@Service
@Transactional
public class CBudgetSubSubcategoriesServiceImpl implements CBudgetSubSubcategoriesService{

    @Autowired
    private CBudgetSubSubcategoriesDao cBudgetSubSubcategoriesDao;

    @Override
    public List<CBudgetSubSubcategories> findAll() {
        return cBudgetSubSubcategoriesDao.findAll();
    }

    @Override
    public CBudgetSubSubcategories findById(Integer id) {
        return cBudgetSubSubcategoriesDao.findById(id);
    }
}
