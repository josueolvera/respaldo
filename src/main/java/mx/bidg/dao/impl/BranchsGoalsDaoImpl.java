package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BranchsGoalsDao;
import mx.bidg.model.BranchsGoals;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 12/09/16.
 */
@Repository
public class BranchsGoalsDaoImpl extends AbstractDao<Integer, BranchsGoals> implements BranchsGoalsDao {

    @Override
    public BranchsGoals save(BranchsGoals entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BranchsGoals findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<BranchsGoals> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public BranchsGoals update(BranchsGoals entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BranchsGoals entity) {
        remove(entity);
        return true;
    }
}
