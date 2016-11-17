package mx.bidg.service.impl;

import mx.bidg.dao.CommissionMultilevelDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.model.CommissionMultilevel;
import mx.bidg.service.CommissionMultilevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC_YAIR on 17/11/2016.
 */
@Service
@Transactional
public class CommissionMultilevelServiceImpl implements CommissionMultilevelService {

    @Autowired
    CommissionMultilevelDao commissionMultilevelDao;

    @Override
    public CommissionMultilevel findById(Integer idCommissionMultilevel) {
        return commissionMultilevelDao.findById(idCommissionMultilevel);
    }

    @Override
    public List<CommissionMultilevel> findAll() {
        return commissionMultilevelDao.findAll();
    }

    @Override
    public CommissionMultilevel update(CommissionMultilevel commissionMultilevel) {
        return commissionMultilevelDao.update(commissionMultilevel);
    }

    @Override
    public CommissionMultilevel save(CommissionMultilevel commissionMultilevel) {
        return commissionMultilevelDao.save(commissionMultilevel);
    }

    @Override
    public boolean delete(CommissionMultilevel commissionMultilevel) {
        commissionMultilevelDao.delete(commissionMultilevel);
        return true;
    }
}
