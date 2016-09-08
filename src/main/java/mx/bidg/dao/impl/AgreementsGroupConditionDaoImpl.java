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

    @Override
    public List<AgreementsGroupCondition> listByAgreementGroup(Integer idAg) {
        Criteria criteria = createEntityCriteria();
        return (List<AgreementsGroupCondition>) criteria
                .add(Restrictions.eq("idAg",idAg))
                .list();
    }

    @Override
    public AgreementsGroupCondition updateStatus(Integer idGroupCondition, boolean statusBoolean) {
        Criteria criteria = createEntityCriteria();
        AgreementsGroupCondition agc = (AgreementsGroupCondition) criteria
                .add(Restrictions.eq("idGroupCondition", idGroupCondition))
                .uniqueResult();
        if (statusBoolean) 
        {
            agc.setStatus(1);
        }
        else{
            agc.setStatus(0);
        }
        modify(agc);
        return agc;
    }
}
