package mx.bidg.service.impl;

import mx.bidg.dao.CCostCenterDao;
import mx.bidg.model.CCostCenter;
import mx.bidg.service.CCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 09/09/16.
 */
@Service
@Transactional
public class CCostCenterServiceImpl implements CCostCenterService {

    @Autowired
    private CCostCenterDao cCostCenterDao;

    @Override
    public List<CCostCenter> findAll() {
        return cCostCenterDao.findAll();
    }

    @Override
    public CCostCenter findById(Integer idCostCenter) {
        return cCostCenterDao.findById(idCostCenter);
    }
}
