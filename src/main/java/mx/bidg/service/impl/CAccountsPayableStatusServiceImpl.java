package mx.bidg.service.impl;

import mx.bidg.dao.CAccountsPayableStatusDao;
import mx.bidg.model.CAccountsPayableStatus;
import mx.bidg.service.CAccountsPayableStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Service
@Transactional
public class CAccountsPayableStatusServiceImpl implements CAccountsPayableStatusService {

    @Autowired
    CAccountsPayableStatusDao cAccountsPayableStatusDao;

    @Override
    public List<CAccountsPayableStatus> findAll() {
        return cAccountsPayableStatusDao.findAll();
    }
}
