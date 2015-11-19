package mx.bidg.service.impl;

import mx.bidg.dao.CRequestCategoriesDao;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.service.CRequestCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 19/11/15.
 */
@Service
@Transactional
public class CRequestCategoriesServiceImpl implements CRequestCategoriesService {

    @Autowired
    CRequestCategoriesDao dao;

    @Override
    public List<CRequestsCategories> findAll() {
        return dao.findAll();
    }
}
