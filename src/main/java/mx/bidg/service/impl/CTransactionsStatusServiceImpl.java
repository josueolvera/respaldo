package mx.bidg.service.impl;

import mx.bidg.dao.CTransactionsStatusDao;
import mx.bidg.model.CTransactionsStatus;
import mx.bidg.service.CTransactionsStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Service
@Transactional
public class CTransactionsStatusServiceImpl implements CTransactionsStatusService {

    @Autowired
    CTransactionsStatusDao cTransactionsStatusDao;

    @Override
    public List<CTransactionsStatus> findAll() {
        return cTransactionsStatusDao.findAll();
    }
}
