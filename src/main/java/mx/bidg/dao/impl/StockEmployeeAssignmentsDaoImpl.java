package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.StockEmployeeAssignmentsDao;
import mx.bidg.model.StockEmployeeAssignments;
import mx.bidg.model.Stocks;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 19/01/16.
 */
@Repository
public class StockEmployeeAssignmentsDaoImpl extends AbstractDao<Integer, StockEmployeeAssignments> implements StockEmployeeAssignmentsDao {
    @Override
    public StockEmployeeAssignments save(StockEmployeeAssignments entity) {
        persist(entity);
        return entity;
    }

    @Override
    public StockEmployeeAssignments findById(int id) {
        return (StockEmployeeAssignments) createEntityCriteria()
                .add(Restrictions.eq("idAssignment", id))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public StockEmployeeAssignments findFor(Stocks stock) {
        return (StockEmployeeAssignments) createEntityCriteria()
                .add(Restrictions.eq("idStock", stock.getIdStock()))
                .add(Restrictions.eq("currentAssignment", 1))
                .setFetchMode("employee", FetchMode.JOIN)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StockEmployeeAssignments> findRecordFor(Stocks stock) {
        return (List<StockEmployeeAssignments>) createEntityCriteria()
                .add(Restrictions.eq("idStock", stock.getIdStock()))
                .add(Restrictions.eq("currentAssignment", 0))
                .setFetchMode("employee", FetchMode.JOIN)
                .setFetchMode("dwEnterprises", FetchMode.JOIN)
                .addOrder(Order.desc("assignmentDate"))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StockEmployeeAssignments> findAll() {
        return (List<StockEmployeeAssignments>) createEntityCriteria()
                .list();
    }

    @Override
    public StockEmployeeAssignments update(StockEmployeeAssignments entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(StockEmployeeAssignments entity) {
        remove(entity);
        return true;
    }
}
