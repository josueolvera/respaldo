package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 26/01/16.
 */
@Repository
public class EmployeesDaoImpl extends AbstractDao<Integer, Employees> implements EmployeesDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<Employees> findSimpleBy(DwEnterprises dwEnterprises) {
        return (List<Employees>) createEntityCriteria()
                .createCriteria("dwEmployeesList", JoinType.INNER_JOIN)
                    .add(Restrictions.eq("idDwEnterprise", dwEnterprises.getIdDwEnterprise()))
                .list();
    }

    @Override
    public Employees findByClaveSap(String claveSap) {
        return (Employees) createEntityCriteria()
                .add(Restrictions.eq("claveSap",claveSap))
                .uniqueResult();
    }

    @Override
    public Employees save(Employees entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Employees findById(int id) {
        return (Employees) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employees> findAll() {
        return (List<Employees>) createEntityCriteria()
                .list();
    }

    @Override
    public Employees update(Employees entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Employees entity) {
        remove(entity);
        return true;
    }
}
