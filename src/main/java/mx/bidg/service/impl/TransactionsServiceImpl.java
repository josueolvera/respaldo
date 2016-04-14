package mx.bidg.service.impl;

import mx.bidg.dao.TransactionsDao;
import mx.bidg.model.Transactions;
import mx.bidg.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Service
@Transactional
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionsDao transactionsDao;

    @Override
    public List<Transactions> findAll() {
        return transactionsDao.findAll();
    }
}
