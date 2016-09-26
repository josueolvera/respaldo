package mx.bidg.service.impl;

import mx.bidg.dao.CommissionAmountGroupBackupDao;
import mx.bidg.model.CommissionAmountGroupBackup;
import mx.bidg.service.CommissionAmountGroupBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 22/09/16.
 */
@Service
@Transactional
public class CommissionAmountGroupBackupServiceImpl implements CommissionAmountGroupBackupService {

    @Autowired
    CommissionAmountGroupBackupDao  commissionAmountGroupBackupDao;

    @Override
    public CommissionAmountGroupBackup save(CommissionAmountGroupBackup commissionAmountGroupBackup) {
        return commissionAmountGroupBackupDao.save(commissionAmountGroupBackup);
    }

    @Override
    public CommissionAmountGroupBackup update(CommissionAmountGroupBackup commissionAmountGroupBackup) {
        return commissionAmountGroupBackupDao.update(commissionAmountGroupBackup);
    }

    @Override
    public CommissionAmountGroupBackup findById(Integer idCommissionAmountGroupBackup) {
        return commissionAmountGroupBackupDao.findById(idCommissionAmountGroupBackup);
    }

    @Override
    public List<CommissionAmountGroupBackup> findAll() {
        return commissionAmountGroupBackupDao.findAll();
    }

    @Override
    public boolean delete(CommissionAmountGroupBackup commissionAmountGroupBackup) {
        commissionAmountGroupBackupDao.delete(commissionAmountGroupBackup);
        return true;
    }
}
