package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CommissionAmountGroupDao;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
@Repository
public class CommissionAmountGroupDaoImpl extends AbstractDao<Integer, CommissionAmountGroup> implements CommissionAmountGroupDao {

    @Override
    public CommissionAmountGroup save(CommissionAmountGroup entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CommissionAmountGroup findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CommissionAmountGroup> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CommissionAmountGroup update(CommissionAmountGroup entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CommissionAmountGroup entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CommissionAmountGroup> getComissionsByConditon(AgreementsGroupCondition agreementsGroupCondition) {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.eq("idAg",agreementsGroupCondition.getIdAg()))
                .add(Restrictions.between("amount",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }

    @Override
    public List<CommissionAmountGroup> getBonusByConditon(AgreementsGroupCondition agreementsGroupCondition) {
        Criteria criteria = createEntityCriteria();

        return criteria.add(Restrictions.eq("idAg",agreementsGroupCondition.getIdAg()))
                .add(Restrictions.between("applicationsNumber",agreementsGroupCondition.getAmountMin(),
                        agreementsGroupCondition.getAmountMax()))
                .list();
    }
}
