package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.InsurancePremiumDao;
import mx.bidg.model.InsurancePremium;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
@Repository
public class InsurancePremiumDaoImpl extends AbstractDao<Integer,InsurancePremium> implements InsurancePremiumDao{
    @Override
    public InsurancePremium save(InsurancePremium entity) {
        persist(entity);
        return entity;
    }

    @Override
    public InsurancePremium findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<InsurancePremium> findAll() {
        return (List<InsurancePremium>)createEntityCriteria().list();
    }

    @Override
    public InsurancePremium update(InsurancePremium entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(InsurancePremium entity) {
        remove(entity);
        return true;
    }

    @Override
    public InsurancePremium findByTypeSecureAndAmountSecure(Integer idTypeSecure, Integer idAmountsSecure) {
        Criteria criteria = createEntityCriteriaNoAccessLevel();

        return (InsurancePremium) criteria
                .add(Restrictions.eq("idAmountsSecure",idAmountsSecure))
                .add(Restrictions.eq("idTypeSecure",idTypeSecure))
                .uniqueResult();
    }
}
