package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.model.DwEmployees;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 26/01/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class EmployeesDaoImpl extends AbstractDao<Integer, Employees> implements EmployeesDao {
    @Override
    public List<Employees> findSimpleBy(DwEnterprises dwEnterprises) {
        return (List<Employees>) createEntityCriteria()
                .createCriteria("dwEmployees", JoinType.INNER_JOIN)
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
    public List<Employees> findByNameAndRfc(String employeeName, String employeeRfc) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        if (employeeName != null) {
            disjunction.add(Restrictions.ilike("firstName",employeeName, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.ilike("middleName",employeeName, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.ilike("parentalLast",employeeName, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.ilike("motherLast",employeeName, MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }

        if (employeeRfc != null) {
            criteria.add(Restrictions.eq("rfc",employeeRfc));
        }

        return criteria.list();
    }

    @Override
    public List<Employees> findBetweenJoinDateAndStatus(String startDate, String endDate, Integer status) {
        Criteria criteria = createEntityCriteria();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        if (endDate != null && startDate != null) {
            LocalDateTime endLocalDateTime = LocalDateTime.parse(endDate + " 23:59:59",formatter);
            LocalDateTime startLocalDateTime = LocalDateTime.parse(startDate + " 00:00:00",formatter);
            criteria.add(Restrictions.between("joinDate",startLocalDateTime,endLocalDateTime));
        }

        if (status != null) {
            criteria.add(Restrictions.eq("status",status));
        }

        return criteria.list();
    }

    @Override
    public List<Employees> findByStatus(Integer status) {
        Criteria criteria = createEntityCriteria();

        if (status != null) {
            criteria.add(Restrictions.eq("status",status));
        }

        return criteria.list();
    }

    @Override
    public Employees findByRfc(String rfc) {
        return (Employees) createEntityCriteria().add(Restrictions.eq("rfc",rfc)).uniqueResult();
    }

    @Override
    public Employees findByCurp(String curp) {
        return (Employees) createEntityCriteria().add(Restrictions.eq("curp", curp)).uniqueResult();
    }

    @Override
    public List<Employees> findByJoinDate(LocalDateTime fromDate, LocalDateTime toDate) {

        Criteria criteria = createEntityCriteria();

        criteria.add(Restrictions.between("joinDate",fromDate,toDate));

        return criteria.add(Restrictions.eq("status",1)).list();
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
