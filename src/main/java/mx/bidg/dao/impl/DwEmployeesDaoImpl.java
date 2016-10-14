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

import java.util.ArrayList;
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
        persist(entity);
        return entity;
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
        Disjunction employeesDisjunction = Restrictions.disjunction();
        Disjunction dwEnterprisesDisjunction = Restrictions.disjunction();

        if (!employees.isEmpty()) {
            for (Employees employee : employees) {
                employeesDisjunction.add(Restrictions.eq("idEmployee", employee.getIdEmployee()));
            }
            criteria.add(employeesDisjunction);
        }

        if (!dwEnterprises.isEmpty()) {
            for (DwEnterprises dwEnterprise : dwEnterprises) {
                dwEnterprisesDisjunction.add(Restrictions.eq("idDwEnterprise", dwEnterprise.getIdDwEnterprise()));
            }
            criteria.add(dwEnterprisesDisjunction);
        }

        if (idRole != null) {
            criteria.add(Restrictions.eq("idRole",idRole));
        }

        if (dwEnterprises.isEmpty() || employees.isEmpty()) {
            return new ArrayList<>();
        }

        return criteria.list();
    }

    @Override
    public List<DwEmployees> findByDwEnterprises(List<DwEnterprises> dwEnterprises) {

        if (dwEnterprises.isEmpty()) {
            return new ArrayList<>();
        }

        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        for (DwEnterprises dwEnterprise : dwEnterprises) {
            disjunction.add(Restrictions.eq("idDwEnterprise", dwEnterprise.getIdDwEnterprise()));
        }
        criteria.add(disjunction);

        return criteria.list();
    }

    @Override
    public List<DwEmployees> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DwEmployees update(DwEmployees entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DwEmployees entity) {
        remove(entity);
        return true;
    }

    @Override
    public DwEmployees findByEmployee(Integer idEmployee) {
        return getByKey(idEmployee);
    }

    @Override
    public List<DwEmployees> findByDwEnterprise(Integer idDwEnterprise) {
        return createEntityCriteria()
                .add(Restrictions.eq("idDwEnterprise",idDwEnterprise))
                .list();
    }

    @Override
    public List<DwEmployees> findDwEmployeeByDwEnterpirseAndRole(Integer idDwEnterprise, Integer idRole) {
        return createEntityCriteria()
                .add(Restrictions.eq("idDwEnterprise", idDwEnterprise))
                .add(Restrictions.eq("idRole", idRole))
                .list();
    }

    @Override
    public List<DwEmployees> findByRolePromotor(List<Employees> employeesList) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionEmployee = Restrictions.disjunction();

        criteria.add(Restrictions.eq("idRole", 64));

        if (employeesList.isEmpty() == false){
            for (Employees employee : employeesList){
                disjunctionEmployee.add(Restrictions.eq("idEmployee", employee.getIdEmployee()));
            }
        }

        criteria.add(disjunctionEmployee);

        return criteria.list();
    }

    @Override
    public DwEmployees findByIdEmployee(Integer idEmployee) {
        return (DwEmployees) createEntityCriteria()
                .add(Restrictions.eq("idEmployee", idEmployee))
                .uniqueResult();
    }
}
