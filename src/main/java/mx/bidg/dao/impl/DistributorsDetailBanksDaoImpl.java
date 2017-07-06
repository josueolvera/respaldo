package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorsDetailBanksDao;
import mx.bidg.model.DistributorsDetailBanks;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Leonardo on 13/06/2017.
 */
@Repository
public class DistributorsDetailBanksDaoImpl extends AbstractDao<Integer, DistributorsDetailBanks> implements DistributorsDetailBanksDao{

    @Override
    public  DistributorsDetailBanks save(DistributorsDetailBanks entity){
        persist(entity);
        return entity;
    }

    @Override
    public  DistributorsDetailBanks findById(int id) {return getByKey(id);}

    @Override
    public List<DistributorsDetailBanks> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DistributorsDetailBanks update (DistributorsDetailBanks entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete (DistributorsDetailBanks entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<DistributorsDetailBanks> getByDistributor(int idDistributor){
        return  createEntityCriteria().add(Restrictions.eq("idDistributor", idDistributor)).list();
    }

    @Override
    public DistributorsDetailBanks findByAccountNumber(String accountNumber){
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("accountNumber",accountNumber));

        return (DistributorsDetailBanks) criteria.uniqueResult();
    }

    @Override
    public BigDecimal sumByDistributor(Integer idDistributor) {
        return (BigDecimal) createEntityCriteria()
                .setProjection(Projections.sum("amount"))
                .add(Restrictions.eq("idDistributor", idDistributor))
                .uniqueResult();
    }

    @Override
    public DistributorsDetailBanks findLikeAccountNumber(String accountNumber){
        Criteria criteria = createEntityCriteria();
        return (DistributorsDetailBanks) criteria
                .add(Restrictions.ilike("accountNumber", accountNumber, MatchMode.ANYWHERE)).uniqueResult();
    }

}
