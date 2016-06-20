/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBranchsDao;
import mx.bidg.model.CBranchs;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@SuppressWarnings("unchecked")
@Repository
public class CBranchsDaoImpl extends AbstractDao<Integer, CBranchs> implements CBranchsDao {

    @Override
    public CBranchs save(CBranchs entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBranchs findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBranchs> findAll() {
        Criteria criteria = createEntityCriteria();
        return criteria
                .setFetchMode("dwEnterprises", FetchMode.JOIN)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public CBranchs update(CBranchs entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBranchs entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
