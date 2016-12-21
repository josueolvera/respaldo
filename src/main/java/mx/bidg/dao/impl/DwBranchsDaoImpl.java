package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DwBranchsDao;
import mx.bidg.model.DwBranchs;
import mx.bidg.model.DwEnterprises;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class DwBranchsDaoImpl extends AbstractDao<Integer, DwBranchs> implements DwBranchsDao {
    @Override
    public DwBranchs save(DwBranchs entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DwBranchs findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DwBranchs> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DwBranchs update(DwBranchs entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DwBranchs entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public BigDecimal sumGoalByZoneOrRegion (List<DwEnterprises> branchsList) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionDw = Restrictions.disjunction();
        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.sum("branchGoal"));

        if (!branchsList.isEmpty()){
            for (DwEnterprises branch : branchsList){
                disjunctionDw.add(Restrictions.eq("idBranch", branch.getBranch().getIdBranch()));
            }
        }

        criteria.add(Restrictions.disjunction(disjunctionDw));
        criteria.setProjection(projectionList);

        return (BigDecimal) criteria.uniqueResult();
    }
}
