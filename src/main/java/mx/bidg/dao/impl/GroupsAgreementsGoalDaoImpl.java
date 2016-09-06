package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.GroupsAgreementsGoalDao;
import mx.bidg.model.GroupsAgreementsGoal;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class GroupsAgreementsGoalDaoImpl extends AbstractDao<Integer, GroupsAgreementsGoal> implements GroupsAgreementsGoalDao {

    @Override
    public GroupsAgreementsGoal save(GroupsAgreementsGoal entity) {
        persist(entity);
        return entity;
    }

    @Override
    public GroupsAgreementsGoal findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<GroupsAgreementsGoal> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public GroupsAgreementsGoal update(GroupsAgreementsGoal entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(GroupsAgreementsGoal entity) {
        remove(entity);
        return true;
    }
}
