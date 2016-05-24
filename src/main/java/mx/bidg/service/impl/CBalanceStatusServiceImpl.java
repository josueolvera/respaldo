package mx.bidg.service.impl;

import mx.bidg.dao.CBalanceStatusDao;
import mx.bidg.model.CBalanceStatus;
import mx.bidg.service.CBalanceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
@Service
@Transactional
public class CBalanceStatusServiceImpl implements CBalanceStatusService {

    @Autowired
    CBalanceStatusDao cBalanceStatusDao;

    @Override
    public List<CBalanceStatus> findAll() {
        return cBalanceStatusDao.findAll();
    }
}
