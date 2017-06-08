package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PayrollDao;
import mx.bidg.model.Payroll;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Desarrollador on 19/11/2016.
 */
@Repository
public class PayrollDaoImpl extends AbstractDao <Integer,Payroll> implements PayrollDao {

    @Override
    public Payroll save(Payroll entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Payroll findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Payroll> findAll() {
        return createEntityCriteria().addOrder(Order.asc("banco")).list();
    }

    @Override
    public Payroll update(Payroll entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Payroll entity) {
        remove(entity);
        return true;
    }

    @Override
    public Object sumEfectivoEdmon() {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("efectivoEdmon"));
        return criteria.setProjection(projectionList).uniqueResult();
    }

    @Override
    public Object sumGmtNec() {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("totalFacturar"));
        return criteria.setProjection(projectionList).add(Restrictions.ge("totalFacturar", new BigDecimal(0.001))).add(Restrictions.ne("numeroDeEmpleado",3220)).uniqueResult();
    }

    @Override
    public List<Payroll> findByDistributor(Integer idDistributor) {
        Criterion pago = Restrictions.ge("pago", new BigDecimal(0.001));
        Criterion comisionNec = Restrictions.ge("comisionNec", new BigDecimal(0.001));
        Criterion totalFacturar = Restrictions.ge("totalFacturar", new BigDecimal(0.001));

        LogicalExpression expression = Restrictions.and(pago,comisionNec);
        LogicalExpression expression2 = Restrictions.and(expression,totalFacturar);
        return createEntityCriteria()
                .add(Restrictions.eq("idDistribuidor", idDistributor))
                .add(expression2)
                .addOrder(Order.asc("banco"))
                .list();
    }

    @Override
    public Object sumDistributorNec(Integer idDistributor) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("totalFacturar"));
        return criteria.setProjection(projectionList)
                .add(Restrictions.ge("totalFacturar", new BigDecimal(0.001)))
                .add(Restrictions.eq("idDistribuidor", idDistributor))
                .uniqueResult();
    }

    @Override
    public List<Payroll> findAllByAmountPositives() {
        Criterion pago = Restrictions.ge("pago", new BigDecimal(0.001));
        Criterion comisionNec = Restrictions.ge("comisionNec", new BigDecimal(0.001));
        Criterion totalFacturar = Restrictions.ge("totalFacturar", new BigDecimal(0.001));

        LogicalExpression expression = Restrictions.and(pago,comisionNec);
        LogicalExpression expression2 = Restrictions.and(expression,totalFacturar);
        return createEntityCriteria()
                .add(expression2)
                .addOrder(Order.asc("banco"))
                .list();
    }

    @Override
    public List<Payroll> findAllByAmountPositivesNotExtranjeros(List<Integer> idEmployeesE) {
        Criterion pago = Restrictions.ge("pago", new BigDecimal(0.001));
        Criterion comisionNec = Restrictions.ge("comisionNec", new BigDecimal(0.001));
        Criterion totalFacturar = Restrictions.ge("totalFacturar", new BigDecimal(0.001));

        Conjunction disjunctionExtranjeros = Restrictions.conjunction();

        if(!idEmployeesE.isEmpty()){
            for(Integer idEmployee : idEmployeesE){
                disjunctionExtranjeros.add(Restrictions.ne("numeroDeEmpleado", idEmployee));
            }
        }


        LogicalExpression expression = Restrictions.and(pago,comisionNec);
        LogicalExpression expression2 = Restrictions.and(expression,totalFacturar);
        return createEntityCriteria()
                .add(Restrictions.conjunction(disjunctionExtranjeros))
                .add(expression2)
                .addOrder(Order.asc("banco"))
                .list();
    }

    @Override
    public Object sumGmtNecNotExtranjeros(List<Integer> idEmployeesE) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();
        Conjunction disjunctionExtranjeros = Restrictions.conjunction();

        if(!idEmployeesE.isEmpty()){
            for(Integer idEmployee: idEmployeesE){
                disjunctionExtranjeros.add(Restrictions.ne("numeroDeEmpleado", idEmployee));
            }
        }


        projectionList.add(Projections.sum("totalFacturar"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.conjunction(disjunctionExtranjeros))
                .add(Restrictions.ge("totalFacturar", new BigDecimal(0.001)))
                .add(Restrictions.ne("numeroDeEmpleado",3220))
                .uniqueResult();
    }

    @Override
    public Object sumCommissionGmtNotExtranjeros(List<Integer> idEmployeesE) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();
        Conjunction disjunctionExtranjeros = Restrictions.conjunction();

        if(!idEmployeesE.isEmpty()){
            for(Integer idEmployee: idEmployeesE){
                disjunctionExtranjeros.add(Restrictions.ne("numeroDeEmpleado", idEmployee));
            }
        }


        projectionList.add(Projections.sum("comisionNec"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.conjunction(disjunctionExtranjeros))
                .add(Restrictions.ge("totalFacturar", new BigDecimal(0.001)))
                .add(Restrictions.ne("numeroDeEmpleado",3220))
                .uniqueResult();
    }

    @Override
    public Object sumPagoGmtNotExtranjeros(List<Integer> idEmployeesE) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();
        Conjunction disjunctionExtranjeros = Restrictions.conjunction();

        if(!idEmployeesE.isEmpty()){
            for(Integer idEmployee: idEmployeesE){
                disjunctionExtranjeros.add(Restrictions.ne("numeroDeEmpleado", idEmployee));
            }
        }


        projectionList.add(Projections.sum("pago"));

        return criteria.setProjection(projectionList)
                .add(Restrictions.conjunction(disjunctionExtranjeros))
                .add(Restrictions.ge("totalFacturar", new BigDecimal(0.001)))
                .add(Restrictions.ne("numeroDeEmpleado",3220))
                .uniqueResult();
    }

    @Override
    public Object sumCommissionDistributorNec(Integer idDistributor) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("comisionNec"));
        return criteria.setProjection(projectionList)
                .add(Restrictions.ge("totalFacturar", new BigDecimal(0.001)))
                .add(Restrictions.eq("idDistribuidor", idDistributor))
                .uniqueResult();
    }

    @Override
    public Object sumPagoDistributorNec(Integer idDistributor) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("pago"));
        return criteria.setProjection(projectionList)
                .add(Restrictions.ge("totalFacturar", new BigDecimal(0.001)))
                .add(Restrictions.eq("idDistribuidor", idDistributor))
                .uniqueResult();
    }
}
