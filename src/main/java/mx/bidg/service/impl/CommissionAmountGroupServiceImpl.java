package mx.bidg.service.impl;

import mx.bidg.dao.CommissionAmountGroupDao;
import mx.bidg.model.CAgreements;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.CommissionAmountGroup;
import mx.bidg.service.CommissionAmountGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
@Service
@Transactional
public class CommissionAmountGroupServiceImpl implements CommissionAmountGroupService {

    @Autowired
    CommissionAmountGroupDao commissionAmountGroupDao;

    @Override
    public CommissionAmountGroup save(CommissionAmountGroup commissionAmountGroup) {
        return commissionAmountGroupDao.save(commissionAmountGroup);
    }

    @Override
    public CommissionAmountGroup update(CommissionAmountGroup commissionAmountGroup) {
        return commissionAmountGroupDao.update(commissionAmountGroup);
    }

    @Override
    public CommissionAmountGroup findById(Integer idCommissionAmount) {
        return commissionAmountGroupDao.findById(idCommissionAmount);
    }

    @Override
    public boolean delete(CommissionAmountGroup commissionAmountGroup) {
        commissionAmountGroupDao.delete(commissionAmountGroup);
        return true;
    }

    @Override
    public List<CommissionAmountGroup> findAll() {
        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyGroup(List list, CAgreementsGroups agreementsGroups) {

        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Long numRequest = (Long) projection[1];
            String claveSap = (String) projection[0];
            BigDecimal amount = (BigDecimal) projection[2];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setClaveSap(claveSap);
            commissionAmountGroup.setApplicationsNumber(numRequest);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyBranch(List list, CAgreementsGroups agreementsGroups) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idBranch = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdBranch(idBranch);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyZona(List list, CAgreementsGroups agreementsGroups) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idZonas = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdZona(idZonas);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyRegion(List list, CAgreementsGroups agreementsGroups) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idRegion = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdRegion(idRegion);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainAmountsbyDistributor(List list, CAgreementsGroups agreementsGroups) {
        for(Object data : list) {
            CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
            Object[] projection = (Object[]) data;
            Integer idDistributor = (Integer) projection[0];
            BigDecimal amount = (BigDecimal) projection[1];

            commissionAmountGroup.setAmount(amount);
            commissionAmountGroup.setIdDistributor(idDistributor);
            commissionAmountGroup.setIdAg(agreementsGroups.getIdAg());
            commissionAmountGroup.setGroupName(agreementsGroups.getAgreementGroupName());
            commissionAmountGroup.setCommission(BigDecimal.valueOf(0));
            commissionAmountGroup.setTabulator(BigDecimal.valueOf(0));
            commissionAmountGroupDao.save(commissionAmountGroup);
        }

        return commissionAmountGroupDao.findAll();
    }
}
