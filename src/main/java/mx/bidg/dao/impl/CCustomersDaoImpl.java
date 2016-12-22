package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CCustomersDao;
import mx.bidg.model.CCustomers;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/12/2016.
 */
@Repository
public class CCustomersDaoImpl extends AbstractDao<Integer,CCustomers> implements CCustomersDao{
    @Override
    public CCustomers save(CCustomers entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CCustomers findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CCustomers> findAll() {
        Criteria criteria= createEntityCriteria();
        return (List<CCustomers>)criteria.list();
    }

    @Override
    public CCustomers update(CCustomers entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(CCustomers entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
