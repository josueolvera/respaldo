package mx.bidg.service;

import mx.bidg.model.CommissionAmountGroupBackup;

import java.util.List;

/**
 * Created by josueolvera on 22/09/16.
 */
public interface CommissionAmountGroupBackupService {

    CommissionAmountGroupBackup save(CommissionAmountGroupBackup commissionAmountGroupBackup);
    CommissionAmountGroupBackup update(CommissionAmountGroupBackup commissionAmountGroupBackup);
    CommissionAmountGroupBackup findById(Integer idCommissionAmountGroupBackup);
    List<CommissionAmountGroupBackup> findAll();
    boolean delete(CommissionAmountGroupBackup commissionAmountGroupBackup);
}
