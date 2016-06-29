/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.model.*;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@SuppressWarnings("unchecked")
@Repository
public class DwEnterprisesDaoImpl extends AbstractDao<Integer, DwEnterprises> implements DwEnterprisesDao {

    @Override
    public DwEnterprises save(DwEnterprises entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DwEnterprises findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DwEnterprises> findAll() {
        return (List<DwEnterprises>) createEntityCriteria()
                .setFetchMode("group", FetchMode.JOIN)
                .setFetchMode("distributor", FetchMode.JOIN)
                .setFetchMode("region", FetchMode.JOIN)
                .setFetchMode("branch", FetchMode.JOIN)
                .setFetchMode("area", FetchMode.JOIN)
                .list();
    }

    @Override
    public DwEnterprises update(DwEnterprises entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DwEnterprises entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DwEnterprises> findByGroupArea(CGroups idGroup, CAreas idArea) {
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("group", idGroup);
        map.put("area", idArea);
        map.put("budgetable", 1);
        return (List<DwEnterprises>) criteria.add(Restrictions.allEq(map)).list();
    }

    @Override
    public List<DwEnterprises> findByDistributor(Integer idDistributor) {

        return (List<DwEnterprises>) getSession().createCriteria(DwEnterprises.class)
                .add(Restrictions.eq("idDistributor", idDistributor)).list();
    }

    @Override
    public DwEnterprises findByDistributorRegionBranch(Integer idDistributor, Integer idRegion, Integer idBranch) {
        return (DwEnterprises) createEntityCriteria()
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idRegion", idRegion))
                .add(Restrictions.eq("idBranch", idBranch))
                .uniqueResult();
    }

    @Override
    public List<DwEnterprises> findByDistributorAndRegionAndBranchAndArea(Integer idDistributor, Integer idRegion, Integer idBranch,Integer idArea) {

        Criteria criteria = createEntityCriteria();

        boolean hasRestrictions = false;

        if (idDistributor != null) {
            criteria.add(Restrictions.eq("idDistributor",idDistributor));
            hasRestrictions = true;
        }
        if (idRegion != null) {
            criteria.add(Restrictions.eq("idRegion",idRegion));
            hasRestrictions = true;
        }
        if (idBranch != null) {
            criteria.add(Restrictions.eq("idBranch",idBranch));
            hasRestrictions = true;
        }
        if (idArea != null) {
            criteria.add(Restrictions.eq("idArea",idArea));
            hasRestrictions = true;
        }

        if (!hasRestrictions) {
            return null;
        }

        return criteria.list();
    }

    @Override
    public DwEnterprises findByBranch(Integer idBranch) {
        return (DwEnterprises) createEntityCriteria()
                .add(Restrictions.eq("idBranch", idBranch))
                .add(Restrictions.eq("status",true))
                .uniqueResult();
    }

    @Override
    public DwEnterprises findByCombination(CGroups group, CDistributors distributor, CRegions region, CBranchs branch, CAreas area) {
        Criteria criteria = createEntityCriteria();
        HashMap<String, Object> map = new HashMap<>();
        map.put("group", group);
        map.put("distributor", distributor);
        map.put("region", region);
        map.put("branch", branch);
        map.put("area", area);
        map.put("budgetable", 1);
        return (DwEnterprises) criteria.add(Restrictions.allEq(map)).uniqueResult();
    }
}
