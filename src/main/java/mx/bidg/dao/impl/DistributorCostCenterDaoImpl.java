package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorCostCenterDao;
import mx.bidg.model.DistributorCostCenter;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 16/03/2017.
 */
@Repository
public class DistributorCostCenterDaoImpl extends AbstractDao<Integer, DistributorCostCenter> implements DistributorCostCenterDao{
    @Override
    public DistributorCostCenter save(DistributorCostCenter entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DistributorCostCenter findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DistributorCostCenter> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DistributorCostCenter update(DistributorCostCenter entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DistributorCostCenter entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<DistributorCostCenter> findByCostCenter(Integer idCostCenter) {
        return createEntityCriteria().add(Restrictions.eq("idCostCenter", idCostCenter)).list();
    }

    @Override
    public DistributorCostCenter findByIdCostCenter(Integer idCostCenter) {
        Criteria criteria = createEntityCriteria();
        return (DistributorCostCenter) criteria.add(Restrictions.eq("idCostCenter",idCostCenter)).uniqueResult();
    }

    @Override
    public List<DistributorCostCenter> findByIdBussinessAndDistributorAndCostCenter(Integer idBussinessLine, Integer idDistributor,Integer idCostCenter) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idBussinessLine",idBussinessLine)).
                add(Restrictions.eq("idDistributor",idDistributor)).
                add(Restrictions.eq("idCostCenter",idCostCenter)).list();
    }

    @Override
    public List<DistributorCostCenter> findByCostCenterAndDistributors(Integer idCostCenter, List<Integer> idDistributors) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionDistributors = Restrictions.disjunction();

        if (!idDistributors.isEmpty()){
            for (Integer idDistributor : idDistributors){
                disjunctionDistributors.add(Restrictions.eq("idDistributor", idDistributor));
            }
        }

        if (idCostCenter != null){
            criteria.add(Restrictions.eq("idCostCenter",idCostCenter));
        }

        criteria.add(Restrictions.disjunction(disjunctionDistributors));
        return criteria.list();
    }

    @Override
    public List<Integer> getIdsDistributorsByBusinessLine(Integer idBusinessLine) {
        return createEntityCriteria().add(Restrictions.eq("idBussinessLine", idBusinessLine))
                .setProjection(Projections.distinct(Projections.property("idDistributor")))
                .list();
    }

    @Override
    public List<Integer> getIdsCostCentersByBDistributor(Integer idDistributor, List<Integer> idsBussinessLines) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionIdsDistributor = Restrictions.disjunction();

        if (!idsBussinessLines.isEmpty()){
            for (Integer idBussinessLine : idsBussinessLines){
                disjunctionIdsDistributor.add(Restrictions.eq("idBussinessLine", idBussinessLine));
            }
        }
        return criteria.add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.disjunction(disjunctionIdsDistributor))
                .setProjection(Projections.distinct(Projections.property("idCostCenter")))
                .list();
    }

    @Override
    public List<DistributorCostCenter> getAllByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionIdsBussinessLines = Restrictions.disjunction();
        Disjunction disjunctionIdsDistributors = Restrictions.disjunction();
        Disjunction disjunctionIdsCCs = Restrictions.disjunction();

        if (!idsBussinessLines.isEmpty()){
            for (Integer idBussinessLine : idsBussinessLines){
                disjunctionIdsBussinessLines.add(Restrictions.eq("idBussinessLine", idBussinessLine));
            }
        }

        if (!idsDistributors.isEmpty()){
            for (Integer idDistributor : idsDistributors){
                disjunctionIdsDistributors.add(Restrictions.eq("idDistributor", idDistributor));
            }
        }

        if (!idsCC.isEmpty()){
            for (Integer idCostCenter : idsCC){
                disjunctionIdsCCs.add(Restrictions.eq("idCostCenter", idCostCenter));
            }
        }

        return criteria
                .add(Restrictions.disjunction(disjunctionIdsBussinessLines))
                .add(Restrictions.disjunction(disjunctionIdsDistributors))
                .add(Restrictions.disjunction(disjunctionIdsCCs))
                .list();
    }

    @Override
    public List<Integer> getIdsCostCentersByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionIdsBussinessLines = Restrictions.disjunction();
        Disjunction disjunctionIdsDistributors = Restrictions.disjunction();
        Disjunction disjunctionIdsCCs = Restrictions.disjunction();

        if (!idsBussinessLines.isEmpty()){
            for (Integer idBussinessLine : idsBussinessLines){
                disjunctionIdsBussinessLines.add(Restrictions.eq("idBussinessLine", idBussinessLine));
            }
        }

        if (!idsDistributors.isEmpty()){
            for (Integer idDistributor : idsDistributors){
                disjunctionIdsDistributors.add(Restrictions.eq("idDistributor", idDistributor));
            }
        }

        if (!idsCC.isEmpty()){
            for (Integer idCostCenter : idsCC){
                disjunctionIdsCCs.add(Restrictions.eq("idCostCenter", idCostCenter));
            }
        }

        return criteria
                .add(Restrictions.disjunction(disjunctionIdsBussinessLines))
                .add(Restrictions.disjunction(disjunctionIdsDistributors))
                .add(Restrictions.disjunction(disjunctionIdsCCs))
                .setProjection(Projections.distinct(Projections.property("idCostCenter")))
                .list();
    }

    @Override
    public List<Integer> getIdsAccountingAccountsByCostCenterAndModuleStatus(Integer idCostCenter, Integer idModuleStatus){

        Criteria criteria = createEntityCriteria();

        if (idCostCenter != null){
            criteria.add(Restrictions.eq("idCostCenter",idCostCenter));
        }

        if (idModuleStatus != null){
            criteria.add(Restrictions.eq("idModuleStatus",idModuleStatus));
        }

        return criteria.setProjection(Projections.distinct(Projections.property("idAccountingAccount"))).list();
    }
}
