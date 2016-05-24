package mx.bidg.service.impl;

import mx.bidg.dao.BalancesDao;
import mx.bidg.model.Balances;
import mx.bidg.service.BalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Service
@Transactional
public class BalancesServiceImpl implements BalancesService {

    @Autowired
    BalancesDao balancesDao;

    @Override
    public List<Balances> findAll() {
        return balancesDao.findAll();
    }

    @Override
    public Balances findById(Integer id) {
        return balancesDao.findById(id);
    }

    @Override
    public Balances save(Balances balance) {
        balancesDao.save(balance);
        return balance;
    }

    @Override
    public Balances update(Balances balance) {
        balancesDao.update(balance);
        return balance;
    }

    @Override
    public boolean delete(Balances balance) {
        balancesDao.delete(balance);
        return true;
    }
}
