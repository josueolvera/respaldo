package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AgreementsGroupConditionDao;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
@Repository
public class AgreementsGroupConditionDaoImpl extends AbstractDao<Integer, AgreementsGroupCondition> implements AgreementsGroupConditionDao {

    @Override
    public AgreementsGroupCondition save(AgreementsGroupCondition entity) {
        persist(entity);
        return entity;
    }

    @Override
    public AgreementsGroupCondition findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<AgreementsGroupCondition> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public AgreementsGroupCondition update(AgreementsGroupCondition entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(AgreementsGroupCondition entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<AgreementsGroupCondition> conditionList(Integer idAg) {
        return createEntityCriteria().add(Restrictions.eq("idAg",idAg)).add(Restrictions.eq("status",1)).list();
    }

    @Override
    public AgreementsGroupCondition getTabulator(Integer orden, AgreementsGroupCondition aGC) {
        Criteria criteria = createEntityCriteria();

        return (AgreementsGroupCondition) criteria.add(Restrictions.eq("order",orden))
                .add(Restrictions.eq("idAg",aGC.getIdAg())).add(Restrictions.eq("status",1))
                .uniqueResult();
    }
}
