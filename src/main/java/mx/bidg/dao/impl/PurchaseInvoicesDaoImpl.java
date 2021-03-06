package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PurchaseInvoicesDao;
import mx.bidg.model.PurchaseInvoices;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
@Repository
public class PurchaseInvoicesDaoImpl extends AbstractDao<Integer, PurchaseInvoices> implements PurchaseInvoicesDao {

    @Override
    public PurchaseInvoices save(PurchaseInvoices entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PurchaseInvoices findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PurchaseInvoices> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PurchaseInvoices update(PurchaseInvoices entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PurchaseInvoices entity) {
        remove(entity);
        return true;
    }

    @Override
    public PurchaseInvoices findByIdRequest(Integer idRequest) {
        return (PurchaseInvoices) createEntityCriteria().add(Restrictions.eq("idRequest", idRequest)).uniqueResult();
    }

    @Override
    public List<PurchaseInvoices> findByRequestTypeAndCatgory(Integer idRequestCategory, Integer idRequestType, Integer idRequestStatus) {
        return createEntityCriteria().createCriteria("request")
                .add(Restrictions.eq("idRequestCategory", idRequestCategory))
                .add(Restrictions.eq("idRequestType", idRequestType))
                .add(Restrictions.eq("idRequestStatus", idRequestStatus))
                .list();
    }
    @Override
    public List<PurchaseInvoices> findFolio(String folio) {
        return createEntityCriteria().createCriteria("request")
                .add(Restrictions.eq("folio", folio))
                .list();
    }

    @Override
    public List<PurchaseInvoices> findBetweenDates (LocalDateTime fromDate, LocalDateTime toDate, Integer status){
        LocalDateTime from = fromDate.toLocalDate().atStartOfDay();
        LocalDateTime to = toDate.toLocalDate().atTime(23, 59, 59);
        Criteria criteria = createEntityCriteria();
        criteria.createCriteria("request")
                .add(Restrictions.eq( "idRequestStatus", status ))
                .add(Restrictions.between("creationDate", from, to));
        return criteria.list();
    }

    @Override
    public List countByDistributor(Integer idDistributor) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .setProjection(Projections.projectionList()
                        .add(Projections.countDistinct("r.idRequest"))
                        .add(Projections.sum("totalAmount")))
                .createCriteria("request", "r")
                .add(Restrictions.eq("r.idRequestStatus", 8))
                .createCriteria("r.distributorCostCenter", "dIS")
                .add(Restrictions.eq("dIS.idDistributor", idDistributor)).list();
    }

}
