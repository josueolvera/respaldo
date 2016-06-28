package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/01/16.
 */
@SuppressWarnings("unchecked")
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
    public List<DwEmployees> findByDwEnterprisesId(DwEnterprises dwEnterprises) {
        return (List<DwEmployees>) createEntityCriteria()
                .add(Restrictions.eq("idDwEnterprise", dwEnterprises.getIdDwEnterprise()))
                .list();
    }

    @Override
    public List<DwEmployees> findByEmployeeAndDwEnterpriseAndRole(List<Employees> employees,List<DwEnterprises> dwEnterprises, Integer idRole) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        if (employees != null) {
            for (Employees employee : employees) {
                disjunction.add(Restrictions.eq("idEmployee",employee.getIdEmployee()));
            }
            criteria.add(disjunction);
        }

        if (dwEnterprises != null) {
            for (DwEnterprises dwEnterprise : dwEnterprises) {
                disjunction.add(Restrictions.eq("idDwEnterprise",dwEnterprise.getIdDwEnterprise()));
            }
            criteria.add(disjunction);
        }

        if (idRole != null) {
            criteria.add(Restrictions.eq("idRole",idRole));
        }

        return criteria.list();
    }


    @Override
    public List<DwEmployees> findAll() {
        return createEntityCriteria().list();
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
