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
import mx.bidg.model.CAreas;
import mx.bidg.model.CBranchs;
import mx.bidg.model.CDistributors;
import mx.bidg.model.CGroups;
import mx.bidg.model.CRegions;
import mx.bidg.model.DwEnterprises;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class DwEnterprisesDaoImpl extends AbstractDao<Integer, DwEnterprises> implements DwEnterprisesDao {

    @Override
    public DwEnterprises save(DwEnterprises entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DwEnterprises findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DwEnterprises> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DwEnterprises update(DwEnterprises entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
