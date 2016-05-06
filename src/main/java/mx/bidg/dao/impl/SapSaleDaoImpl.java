package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.SapSaleDao;
import mx.bidg.model.SapSale;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 29/04/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class SapSaleDaoImpl extends AbstractDao<Integer, SapSale> implements SapSaleDao {
    @Override
    public SapSale save(SapSale entity) {
        persist(entity);
        return entity;
    }

    @Override
    public SapSale findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<SapSale> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<SapSale>) criteria.list();
    }

    @Override
    public SapSale update(SapSale entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(SapSale entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public SapSale findByIdSale(String idSale) {
        // esta mal
        return (SapSale) getSession().createCriteria(SapSale.class)
                .add(Restrictions.eq("idSale", idSale)).uniqueResult();
    }
}
