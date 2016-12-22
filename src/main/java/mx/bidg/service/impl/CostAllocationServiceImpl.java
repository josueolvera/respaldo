package mx.bidg.service.impl;

import mx.bidg.dao.CostAllocationDao;
import mx.bidg.model.CostAllocation;
import mx.bidg.service.CostAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 20/12/2016.
 */
@Repository
@Transactional
public class CostAllocationServiceImpl implements CostAllocationService {

    @Autowired
    private CostAllocationDao costAllocationDao;

    @Override
    public CostAllocation save(CostAllocation costAllocation) {
        return costAllocationDao.save(costAllocation);
    }

    @Override
    public CostAllocation update(CostAllocation costAllocation) {
        return costAllocationDao.update(costAllocation);
    }

    @Override
    public CostAllocation findById(Integer idCA) {
        return costAllocationDao.findById(idCA);
    }

    @Override
    public List<CostAllocation> findAll() {
        return costAllocationDao.findAll();
    }

    @Override
    public boolean delete(CostAllocation costAllocation) {
        costAllocationDao.delete(costAllocation);
        return true;
    }
}
