package mx.bidg.service.impl;

import mx.bidg.dao.CommissionAmountGroupBackupDao;
import mx.bidg.model.CommissionAmountGroupBackup;
import mx.bidg.service.CommissionAmountGroupBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public List<CommissionAmountGroupBackup> findByAcumulateEmployee(Integer idEmployee, LocalDateTime fromDate, LocalDateTime toDate) {
        List<CommissionAmountGroupBackup> commissionAmountGroupBackupList = new ArrayList<>();

        List commission = commissionAmountGroupBackupDao.findAcumulateBySupervisor(idEmployee, fromDate, toDate);

        for (Object data : commission){
            CommissionAmountGroupBackup commissionAmountGroup = new CommissionAmountGroupBackup();
            Object[] projection = (Object[]) data;
            BigDecimal numRequest = new BigDecimal(projection[2].toString());
            BigDecimal commissionAmount  = (BigDecimal) projection[3];
            BigDecimal amount = (BigDecimal) projection[1];
            Integer idAg = (Integer) projection[0];
            Integer idEmploye = (Integer) projection[4];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setIdAg(idAg);
            commissionAmountGroup.setCommission(commissionAmount);
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroup.setFromDate(fromDate);
            commissionAmountGroup.setToDate(toDate);
            commissionAmountGroup.setIdEmployee(idEmploye);

            commissionAmountGroupBackupList.add(commissionAmountGroup);
        }

        return commissionAmountGroupBackupList;
    }
}
