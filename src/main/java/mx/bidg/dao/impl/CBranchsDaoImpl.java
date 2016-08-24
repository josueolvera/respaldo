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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
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
        remove(entity);
        return true;
    }

    @Override
    public CBranchs findByName(String branchName) {
        Criteria criteria = createEntityCriteria();
        return (CBranchs) criteria.add(Restrictions.eq("branchNameClean", branchName)).uniqueResult();
    }

    @Override
    public List<CBranchs> findBySaemFlag(Integer idBranch,Integer saemFlag) {
        Criteria criteria = createEntityCriteria();

        if (idBranch != null){
            criteria.add(Restrictions.eq("idBranch",idBranch));
        }

        criteria.add(Restrictions.eq("saemFlag", saemFlag));

        return criteria.list();
    }
}
