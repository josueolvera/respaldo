package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.GroupAgreementsGoalPercentageDao;
import mx.bidg.model.GroupAgreementsGoalPercentage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class GroupAgreementsGoalPercentageDaoImpl extends AbstractDao<Integer, GroupAgreementsGoalPercentage> implements GroupAgreementsGoalPercentageDao {

    @Override
    public GroupAgreementsGoalPercentage save(GroupAgreementsGoalPercentage entity) {
        persist(entity);
        return entity;
    }

    @Override
    public GroupAgreementsGoalPercentage findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<GroupAgreementsGoalPercentage> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public GroupAgreementsGoalPercentage update(GroupAgreementsGoalPercentage entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(GroupAgreementsGoalPercentage entity) {
        remove(entity);
        return true;
    }
}
