package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.GroupsAgreementsCalculationDao;
import mx.bidg.model.GroupsAgreementsCalculation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class GroupsAgreementsCalculationDaoImpl extends AbstractDao <Integer, GroupsAgreementsCalculation> implements GroupsAgreementsCalculationDao {

    @Override
    public GroupsAgreementsCalculation save(GroupsAgreementsCalculation entity) {
        persist(entity);
        return entity;
    }

    @Override
    public GroupsAgreementsCalculation findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<GroupsAgreementsCalculation> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public GroupsAgreementsCalculation update(GroupsAgreementsCalculation entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(GroupsAgreementsCalculation entity) {
        remove(entity);
        return true;
    }
}
