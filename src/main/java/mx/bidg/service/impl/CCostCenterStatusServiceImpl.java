package mx.bidg.service.impl;

import mx.bidg.dao.CCostCenterStatusDao;
import mx.bidg.model.CCostCenterStatus;
import mx.bidg.service.CCostCenterStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Desarrollador on 05/04/2017.
 */
@Service
@Transactional
public class CCostCenterStatusServiceImpl implements CCostCenterStatusService {

    @Autowired
    CCostCenterStatusDao cCostCenterStatusDao;

    @Override
    public CCostCenterStatus save(CCostCenterStatus cCostCenterStatus) {
        return cCostCenterStatusDao.save(cCostCenterStatus);
    }

    @Override
    public CCostCenterStatus update(CCostCenterStatus cCostCenterStatus) {
        return cCostCenterStatusDao.update(cCostCenterStatus);
    }

    @Override
    public CCostCenterStatus findById(Integer idCCostCenterStatus) {
        return cCostCenterStatusDao.findById(idCCostCenterStatus);
    }

    @Override
    public List<CCostCenterStatus> findAll() {
        return cCostCenterStatusDao.findAll();
    }

    @Override
    public boolean delete(CCostCenterStatus cCostCenterStatus) {
        return cCostCenterStatusDao.delete(cCostCenterStatus);
    }
}
