package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RolesGroupAgreementsDao;
import mx.bidg.model.RolesGroupAgreements;
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
    public List<RolesGroupAgreements> findByRole(Integer idRole) {
        return createEntityCriteria().add(Restrictions.eq("idCalculationRole",idRole)).list();
    }
}
