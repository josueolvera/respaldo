/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CGroupsDao;
import mx.bidg.model.CGroups;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CGroupsDaoImpl extends AbstractDao<Integer, CGroups> implements CGroupsDao {

    @Override
    public CGroups save(CGroups entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CGroups findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CGroups> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CGroups>) criteria.list();
    }

    @Override
    public CGroups update(CGroups entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CGroups entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CGroups getByIdBudgetsCatalogs(Integer idGroup) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idGroup))
                .setFetchMode("budgetsList", FetchMode.JOIN)
//                .setFetchMode("budgetsList.idBudget", FetchMode.SELECT)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        
        return (CGroups) criteria.uniqueResult();
    }
    
}
