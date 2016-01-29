package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/01/16.
 */
@Repository
public class DwEmployeesDaoImpl extends AbstractDao<Integer, DwEmployees> implements DwEmployeesDao {
    @Override
    public DwEmployees save(DwEmployees entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public DwEmployees findById(int id) {
        return (DwEmployees) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public DwEmployees findBy(Employees employee, DwEnterprises dwEnterprises) {
        return (DwEmployees) createEntityCriteria()
                .add(Restrictions.eq("idEmployee", employee.getIdEmployee()))
                .add(Restrictions.eq("idDwEnterprise", dwEnterprises.getIdDwEnterprise()))
                .uniqueResult();
    }

    @Override
    public List<DwEmployees> findAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public DwEmployees update(DwEmployees entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(DwEmployees entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
