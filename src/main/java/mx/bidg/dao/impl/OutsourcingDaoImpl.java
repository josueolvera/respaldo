package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.OutsourcingDao;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.EmployeesHistory;
import mx.bidg.model.Outsourcing;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class OutsourcingDaoImpl extends AbstractDao<Integer, Outsourcing> implements OutsourcingDao {
    @Override
    public Outsourcing save(Outsourcing entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Outsourcing findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Outsourcing> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public Outsourcing update(Outsourcing entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Outsourcing entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Outsourcing finfByidEmployee(int idEmployee,LocalDateTime applicationDate) {
        return (Outsourcing) createEntityCriteria()
                .add(Restrictions.eq("idEmployee",idEmployee))
                .add(Restrictions.eq("applicationDate",applicationDate))
                .setMaxResults(1).uniqueResult();
    }

    @Override
    public List<Outsourcing> findByDwEnterprise(List<DwEnterprises> dwEnterprisesList, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionEnterprise = Restrictions.disjunction();

        if (!dwEnterprisesList.isEmpty()){
            for (DwEnterprises dwEnterprise : dwEnterprisesList){
                disjunctionEnterprise.add(Restrictions.eq("idDwEnterprise",dwEnterprise.getIdDwEnterprise()));
            }
        }

        criteria.add(disjunctionEnterprise);
        criteria.add(Restrictions.between("applicationDate",applicatioDateStart,applicationDateEnd));
        criteria.add(Restrictions.eq("status",1));
        return criteria.list();
    }

    @Override
    public List<Outsourcing> findByType(Integer type, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.between("applicationDate",applicatioDateStart,applicationDateEnd));
        criteria.add(Restrictions.eq("status",1));
        return criteria.list();
    }

    @Override
    public Object findSumRhmasByDwEnterprise(List<DwEnterprises> dwEnterprisesList, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();
        Disjunction disjunctionEnterprise = Restrictions.disjunction();

        projectionList.add(Projections.sum("total"));

        if (!dwEnterprisesList.isEmpty()){
            for (DwEnterprises dwEnterprise : dwEnterprisesList){
                disjunctionEnterprise.add(Restrictions.eq("idDwEnterprise",dwEnterprise.getIdDwEnterprise()));
            }
        }

        criteria.setProjection(projectionList);
        criteria.add(disjunctionEnterprise);
        criteria.add(Restrictions.between("applicationDate",applicatioDateStart,applicationDateEnd));
        criteria.add(Restrictions.eq("status",1));
        return criteria.uniqueResult();
    }

    @Override
    public Object sumRhmasByDwEnterpriseAndType(List<DwEnterprises> dwEnterprisesList, Integer type, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();
        Disjunction disjunctionEnterprise = Restrictions.disjunction();

        projectionList.add(Projections.sum("total"));

        if (!dwEnterprisesList.isEmpty()){
            for (DwEnterprises dwEnterprise : dwEnterprisesList){
                disjunctionEnterprise.add(Restrictions.eq("idDwEnterprise",dwEnterprise.getIdDwEnterprise()));
            }
        }

        criteria.setProjection(projectionList);
        criteria.add(disjunctionEnterprise);
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.between("applicationDate",applicatioDateStart,applicationDateEnd));
        criteria.add(Restrictions.eq("status",1));
        return criteria.uniqueResult();
    }

    @Override
    public List<Outsourcing> findByAllEmployeesAndApplicationDate(List<EmployeesHistory> employeesHistoryList, LocalDateTime iniialDate, LocalDateTime finalDate) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionEmployees = Restrictions.disjunction();

        if (!employeesHistoryList.isEmpty()){
            for (EmployeesHistory employeesHistory : employeesHistoryList){
                disjunctionEmployees.add(Restrictions.eq("idEmployee", employeesHistory.getIdEmployee()));
            }
        }

        criteria.add(disjunctionEmployees);
        criteria.add(Restrictions.between("applicationDate",iniialDate,finalDate));
        return criteria.list();
    }
}
