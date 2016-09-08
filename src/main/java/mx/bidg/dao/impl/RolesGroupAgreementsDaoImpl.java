package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RolesGroupAgreementsDao;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.RolesGroupAgreements;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Repository
public class RolesGroupAgreementsDaoImpl extends AbstractDao<Integer, RolesGroupAgreements> implements RolesGroupAgreementsDao {

    @Override
    public RolesGroupAgreements save(RolesGroupAgreements entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RolesGroupAgreements findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RolesGroupAgreements> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RolesGroupAgreements update(RolesGroupAgreements entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RolesGroupAgreements entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<RolesGroupAgreements> findByRole(Integer idRole, List<CAgreementsGroups> cAgreementsGroupsList) {
        Criteria criteria = createEntityCriteria();
        Disjunction groupDisjunction =  Restrictions.disjunction();

        for (CAgreementsGroups agreementsGroups : cAgreementsGroupsList){
            groupDisjunction.add(Restrictions.eq("idAg", agreementsGroups.getIdAg()));
        }

        criteria.add(groupDisjunction);

        return criteria.add(Restrictions.eq("idCalculationRole",idRole))
                .add(Restrictions.eq("hasGroup",true))
                .addOrder(Order.asc("idAg"))
                .list();
    }

    @Override
    public List<RolesGroupAgreements> findByGroup(Integer idAg) {
        return createEntityCriteria().add(Restrictions.eq("idAg", idAg)).list();
    }

    @Override
    public List<RolesGroupAgreements> findByRoles(Integer idRole, List<CAgreementsGroups> groupsList) {
        Criteria criteria = createEntityCriteria();
        Disjunction groupDisjunction =  Restrictions.disjunction();

        for (CAgreementsGroups agreementsGroups : groupsList){
            groupDisjunction.add(Restrictions.eq("idAg", agreementsGroups.getIdAg()));
        }

        criteria.add(groupDisjunction);

        return criteria.add(Restrictions.eq("idCalculationRole",idRole))
                .addOrder(Order.asc("idAg"))
                .list();
    }
}
