/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.Requests;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.*;
import org.springframework.expression.spel.ast.*;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class RequestsDaoImpl extends AbstractDao<Integer, Requests> implements RequestsDao {

    @Override
    public Requests save(Requests entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Requests findById(int id) {
        return getByKey(id);
    }

    @Override
    public Requests findByFolio(String folio) {
        return (Requests) createEntityCriteria()
                .add(Restrictions.eq("folio", folio))
                .uniqueResult();
    }

    @Override
    public List<Requests> findAll() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return createEntityCriteria().list();
    }

    @Override
    public Requests update(Requests entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Requests entity) {
        remove(entity);
        return true;
    }

    @Override
    public Requests findByIdFetchBudgetMonthBranch(Integer idRequest) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idRequest))
                .setFetchMode("budgetMonthBranch", FetchMode.JOIN);
        return (Requests) criteria.uniqueResult();
    }

    @Override
    public Requests findByIdFetchStatus(Integer idRequest) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idRequest))
                .setFetchMode("requestStatus", FetchMode.JOIN);
        return (Requests) criteria.uniqueResult();
    }

    @Override
    public Requests findByIdFetchCategory(Integer idRequest) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idRequest))
                .setFetchMode("requestTypeProduct", FetchMode.JOIN)
                .setFetchMode("requestTypeProduct.requestCategory", FetchMode.JOIN);
        return (Requests) criteria.uniqueResult();
    }

    @Override
    public List<Requests> findByRequestCategory(Integer idRequestCategory){

        Criteria criteria = createEntityCriteria();

        if(idRequestCategory != null){
            criteria.add(Restrictions.eq("idRequestCategory", idRequestCategory));
        }

        return criteria.list();
    }

    @Override
    public List<Requests> findByCategoryAndTypeByEmployee(Integer idRequestCategory, Integer idRequestType, Integer idEmployee) {
        return createEntityCriteria()
                .add(Restrictions.eq("idRequestCategory", idRequestCategory))
                .add(Restrictions.eq("idRequestType", idRequestType))
                .add(Restrictions.eq("idEmployee", idEmployee))
                .list();
    }

    @Override
    public List<Requests> findByCategoryAndTypeAndStatus(Integer idRequestCategory, Integer idRequestType) {

        Criterion pendiante = Restrictions.eq("idRequestStatus", 1);
        Criterion pCompra = Restrictions.eq("idRequestStatus", 2);
        Criterion pPago = Restrictions.eq("idRequestStatus", 3);
        Criterion vPlaneacion = Restrictions.eq("idRequestStatus", 4);
        Criterion vNivel = Restrictions.eq("idRequestStatus", 5);

        LogicalExpression expression = Restrictions.or(pendiante, pCompra);
        LogicalExpression expression1 = Restrictions.or(expression, pPago);
        LogicalExpression expression2 = Restrictions.or(expression1, vPlaneacion);
        LogicalExpression expression3 = Restrictions.or(expression2, vNivel);


        return createEntityCriteria()
                .add(Restrictions.eq("idRequestCategory", idRequestCategory))
                .add(Restrictions.eq("idRequestType", idRequestType))
                .add(expression3)
                .list();
    }

    @Override
    public List<Requests> findByCategoryAndType(Integer idRequestCategory, Integer idRequestType) {
        return createEntityCriteria()
                .add(Restrictions.eq("idRequestCategory", idRequestCategory))
                .add(Restrictions.eq("idRequestType", idRequestType))
                .list();
    }

    @Override
    public  List<Requests> findByDCC (List<Integer> idDCCs){
        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        if (!idDCCs.isEmpty()){
            for (Integer idDCC : idDCCs){
                disjunction.add(Restrictions.eq("idDistributorCostCenter", idDCC));
            }
        }

        return  criteria.add(disjunction).add(Restrictions.eq("idRequestStatus", 8)).list();
    }

    @Override
    public List<Requests> findListByFolio(String folio) {
        Criteria criteria = createEntityCriteria();
        return criteria
                .add(Restrictions.eq("idRequestStatus", 8))
                .add(Restrictions.ilike("folio", folio, MatchMode.ANYWHERE)).list();
    }
    @Override
    public List<Requests> findByStatus(Integer idRequestStatus) {
        return createEntityCriteria()
                .add(Restrictions.eq("idRequestStatus", idRequestStatus))
                .add(Restrictions.eq("idRequestCategory", 1))
                .add(Restrictions.eq("idRequestType", 1))
                .list();
    }

    @Override
    public List<Requests> findByTotalExpended(BigDecimal authorizedAmount) {
        return createEntityCriteria()
                .add(Restrictions.le("totalExpended", authorizedAmount))
                .add(Restrictions.eq("idRequestStatus", 5))
                .add(Restrictions.eq("idRequestCategory", 1))
                .add(Restrictions.eq("idRequestType", 1))
                .list();
    }

    @Override
    public List<Requests> findAllWithStatusEight(){
        return createEntityCriteria()
                .add(Restrictions.eq("idRequestStatus", 8))
                .list();
    }

    @Override
    public List<Integer> findAllIdRequestsWithStatusEight() {
        return createEntityCriteria().setProjection(Projections.distinct(Projections.property("idDistributorCostCenter")))
                .add(Restrictions.eq("idRequestStatus", 8))
                .list();
    }

    @Override
    public List countByDistributor(Integer idDistributor) {

        return  createEntityCriteria()
                .setProjection(Projections.projectionList()
                        .add(Projections.countDistinct("idRequest"))
                        .add(Projections.sum("totalExpended")))
                .add(Restrictions.eq("idRequestStatus", 8))
                .createCriteria("distributorCostCenter")
                .add(Restrictions.eq("idDistributor", idDistributor)).list();
    }

    @Override
    public List<Requests> findByDistributor(Integer idDistributor) {
        return createEntityCriteria()
                .add(Restrictions.eq("idRequestStatus", 8))
                .createCriteria("distributorCostCenter")
                .add(Restrictions.eq("idDistributor", idDistributor)).list();
    }
}
