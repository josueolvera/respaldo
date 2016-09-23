package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CommissionAmountGroupBackupDao;
import mx.bidg.model.CommissionAmountGroupBackup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 22/09/16.
 */
@Repository
public class CommissionAmountGroupBackupDaoImpl extends AbstractDao<Integer, CommissionAmountGroupBackup> implements CommissionAmountGroupBackupDao {

    @Override
    public CommissionAmountGroupBackup save(CommissionAmountGroupBackup entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CommissionAmountGroupBackup findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CommissionAmountGroupBackup> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CommissionAmountGroupBackup update(CommissionAmountGroupBackup entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CommissionAmountGroupBackup entity) {
        remove(entity);
        return true;
    }
}
