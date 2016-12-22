package mx.bidg.service.impl;

import mx.bidg.dao.CCustomersDao;
import mx.bidg.model.CCustomers;
import mx.bidg.service.CCustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/12/2016.
 */
@Service
@Transactional
public class CCustomersServiceImpl implements CCustomersService{

    @Autowired
    private CCustomersDao cCustomersDao;

    @Override
    public List<CCustomers> findAll() {
        return cCustomersDao.findAll();
    }

    @Override
    public CCustomers findById(Integer id) {
        return cCustomersDao.findById(id);
    }
}
