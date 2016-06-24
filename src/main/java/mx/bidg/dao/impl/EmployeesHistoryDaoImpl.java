package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.model.EmployeesHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class EmployeesHistoryDaoImpl extends AbstractDao<Integer, EmployeesHistory> implements EmployeesHistoryDao {
    @Override
    public EmployeesHistory save(EmployeesHistory entity) {
        persist(entity);
        return null;
    }

    @Override
    public EmployeesHistory findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<EmployeesHistory> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public EmployeesHistory update(EmployeesHistory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(EmployeesHistory entity) {
        remove(entity);
        return false;
    }
}
