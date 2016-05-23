package mx.bidg.service.impl;

import mx.bidg.dao.CTicketStatusDao;
import mx.bidg.model.CTicketStatus;
import mx.bidg.service.CTicketStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@Transactional
@Service
public class CTicketStatusServiceImpl implements CTicketStatusService {

    @Autowired
    private CTicketStatusDao cTicketStatusDao;

    @Override
    public CTicketStatus findById(int id) {
        return cTicketStatusDao.findById(id);
    }

    @Override
    public List<CTicketStatus> findAll() {
        return cTicketStatusDao.findAll();
    }
}
