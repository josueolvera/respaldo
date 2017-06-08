package mx.bidg.service.impl;

import mx.bidg.dao.CRequestStatusDao;
import mx.bidg.model.CRequestStatus;
import mx.bidg.service.CRequestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desarrollador on 2/06/17.
 */
@Service
@Transactional
public class CRequestStatusServiceImpl implements CRequestStatusService {

    @Autowired
    CRequestStatusDao cRequestStatusDao;

    @Override
    public CRequestStatus save(CRequestStatus cRequestStatus) {
        return cRequestStatusDao.save(cRequestStatus);
    }

    @Override
    public CRequestStatus update(CRequestStatus cRequestStatus) {
        return cRequestStatusDao.update(cRequestStatus);
    }

    @Override
    public CRequestStatus findById(Integer idCRequestStatus) {
        return cRequestStatusDao.findById(idCRequestStatus);
    }

    @Override
    public List<CRequestStatus> findAll() {
        return cRequestStatusDao.findAll();
    }

    @Override
    public boolean delete(CRequestStatus cRequestStatus) {
        return cRequestStatusDao.delete(cRequestStatus);
    }
}
