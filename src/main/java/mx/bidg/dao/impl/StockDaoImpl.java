package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.StockDao;
import mx.bidg.model.Stocks;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 8/12/15.
 */
@Repository
public class StockDaoImpl extends AbstractDao<Integer, Stocks> implements StockDao {
    @Override
    public Stocks save(Stocks entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Stocks findById(int id) {
        return (Stocks) createEntityCriteria().add(Restrictions.eq("idStock", id)).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Stocks> findAll() {
        return (List<Stocks>) createEntityCriteria().setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .setFetchMode("propertiesList", FetchMode.JOIN).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Stocks> findByDistributor(Integer idDistributor) {
        return (List<Stocks>) createEntityCriteria().setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .setFetchMode("propertiesList", FetchMode.JOIN)
                .createCriteria("dwEnterprises")
                    .add(Restrictions.eq("iddistributor", idDistributor))
                .list();
    }

    @Override
    public Stocks update(Stocks entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(Stocks entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
