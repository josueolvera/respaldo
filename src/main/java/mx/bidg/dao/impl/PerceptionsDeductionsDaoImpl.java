package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PerceptionsDeductionsDao;
import mx.bidg.model.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Repository
public class PerceptionsDeductionsDaoImpl extends AbstractDao<Integer, PerceptionsDeductions> implements PerceptionsDeductionsDao {

    @Override
    public PerceptionsDeductions save(PerceptionsDeductions entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PerceptionsDeductions findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PerceptionsDeductions> findAll() {
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.asc("applicationDate"));
        return criteria.list();
    }

    @Override
    public PerceptionsDeductions update(PerceptionsDeductions entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PerceptionsDeductions entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<PerceptionsDeductions> findAllWithStatus() {
        return createEntityCriteria().add(Restrictions.eq("status", true)).list();
    }

    @Override
    public List calculateBonus(SqlQueries sqlQueries, Users user, String ofDate, String untilDate) {

        SQLQuery sqlQuery = (SQLQuery) getSession().createSQLQuery(
                sqlQueries.getSqlQuery())
                .setParameter("usuario", user.getUsername())
                .setParameter("fechaInicial", ofDate)
                .setParameter("fechaFinal", untilDate)
                ;

        return  sqlQuery.list();
    }

    @Override
    public List<PerceptionsDeductions> findByIdEmployeeAndApplicationDate(Integer idEmployee, LocalDateTime ofDate, LocalDateTime untilDate) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("idEmployee", idEmployee))
                .add(Restrictions.between("applicationDate",ofDate,untilDate))
                .add(Restrictions.eq("status", true))
                .list();
    }

    @Override
    public List<PerceptionsDeductions> findByAllEmployeesAndInitialDateAndFinalDate(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionEmployees = Restrictions.disjunction();

        if (!employeesHistoryList.isEmpty()){
            for (EmployeesHistory employeesHistory : employeesHistoryList){
                disjunctionEmployees.add(Restrictions.eq("idEmployee",employeesHistory.getIdEmployee()));
            }
        }

        criteria.add(disjunctionEmployees);

        return criteria.add(Restrictions.between("applicationDate",initialDate,finalDate)).list();
    }

    @Override
    public List<PerceptionsDeductions> findByIdEmployee(Integer idEmployee) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idEmployee", idEmployee)).list();
    }

    @Override
    public List<PerceptionsDeductions> findByStartDateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.between("applicationDate",startDate,endDate)).list();
    }

    @Override
    public List<PerceptionsDeductions> findIdEmployeeAndActives(Integer idEmployee) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idEmployee", idEmployee))
                .add(Restrictions.eq("status", true)).list();
    }

    @Override
    public List<PerceptionsDeductions> findByIdEmployeeAndApplicationDateAll(Integer idEmployee, LocalDateTime ofDate, LocalDateTime untilDate) {
        Criteria criteria = createEntityCriteria();

        return criteria
                .add(Restrictions.eq("idEmployee", idEmployee))
                .add(Restrictions.between("applicationDate",ofDate,untilDate))
                .list();

    }

    @Override
    public List findByAllEmployeesAndInitialDateAndFinalDatePerception(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate, List<CPerceptionsDeductions> cPerceptionsDeductionsList) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionEmployees = Restrictions.disjunction();
        Disjunction disjunctionPD = Restrictions.disjunction();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("idEmployee")));
        projList.add(Projections.sum("amount"));

        if (!employeesHistoryList.isEmpty()){
            for (EmployeesHistory employeesHistory : employeesHistoryList){
                disjunctionEmployees.add(Restrictions.eq("idEmployee",employeesHistory.getIdEmployee()));
            }
        }

        if (!cPerceptionsDeductionsList.isEmpty()){
            for (CPerceptionsDeductions cPerceptionsDeductions : cPerceptionsDeductionsList){
                disjunctionPD.add(Restrictions.eq("idCPd",cPerceptionsDeductions.getIdCPd()));
            }
        }

        criteria.setProjection(projList);
        criteria.add(disjunctionPD);
        criteria.add(disjunctionEmployees);

        return criteria.add(Restrictions.between("applicationDate",initialDate,finalDate)).list();
    }

}
