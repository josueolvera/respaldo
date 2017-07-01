package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CsbPayCommissionDao;
import mx.bidg.model.CsbPayCommission;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Serch on 29/06/2017.
 */
@Repository
public class CsbPayCommissionDaoImpl extends AbstractDao<Integer, CsbPayCommission> implements CsbPayCommissionDao {

    @Override
    public CsbPayCommission save(CsbPayCommission entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CsbPayCommission findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CsbPayCommission> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CsbPayCommission update(CsbPayCommission entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CsbPayCommission entity) {
        remove(entity);
        return true;
    }

    @Override
    public CsbPayCommission findByIdSale(String idSale) {
        return (CsbPayCommission) createEntityCriteria()
                .add(Restrictions.eq("idSale", idSale))
                .setFetchMode("sapSale", FetchMode.JOIN)
                .uniqueResult();
    }

    @Override
    public List<CsbPayCommission> findAllByIdSale(String idSale) {
        return createEntityCriteria()
                .add(Restrictions.eq("idSale", idSale))
                .setFetchMode("sapSale", FetchMode.JOIN)
                .list();
    }

    @Override
    public BigDecimal sumAmountByDateAndClaveSap(String claveSap, LocalDate initialDate, LocalDate finalDate) {
        Criteria criteria = createEntityCriteria();
        return (BigDecimal) criteria
                .setProjection(Projections.sum("amountCommission"))
                .add(Restrictions.eq("claveSap", claveSap))
                .add(Restrictions.between("paymentDate", initialDate, finalDate))
                .uniqueResult();
    }
}
