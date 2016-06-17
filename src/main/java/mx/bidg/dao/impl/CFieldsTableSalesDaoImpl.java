package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CFieldsTableSalesDao;
import mx.bidg.model.CFieldsTableSales;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 17/06/16.
 */
@Repository
public class CFieldsTableSalesDaoImpl extends AbstractDao<Integer, CFieldsTableSales> implements CFieldsTableSalesDao {

    @Override
    public CFieldsTableSales save(CFieldsTableSales entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CFieldsTableSales findById(int id) {
        return (CFieldsTableSales) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CFieldsTableSales> findAll() {
        return (List<CFieldsTableSales>) createEntityCriteria()
                .list();
    }

    @Override
    public CFieldsTableSales update(CFieldsTableSales entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CFieldsTableSales entity) {
        remove(entity);
        return true;
    }
}
