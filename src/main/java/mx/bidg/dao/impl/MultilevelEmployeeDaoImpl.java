/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.MultilevelEmployeeDao;
import mx.bidg.model.MultilevelEmployee;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MultilevelEmployeeDaoImpl extends AbstractDao <Integer, MultilevelEmployee> implements MultilevelEmployeeDao {

    @Override
    public MultilevelEmployee save(MultilevelEmployee entity) {
        persist(entity);
        return entity;
    }

    @Override
    public MultilevelEmployee findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<MultilevelEmployee> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public MultilevelEmployee update(MultilevelEmployee entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(MultilevelEmployee entity) {
        remove(entity);
        return true;
    }

    @Override
    public List findByIdBranch(Integer idBranch) {
        Criteria criteria = createEntityCriteria();
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.distinct(Projections.groupProperty("idEmployeeMultilevel")));

        return criteria.setProjection(projList).add(Restrictions.eq("idBranch",idBranch)).list();
    }

    @Override
    public List<MultilevelEmployee> findByIdEmployeeMultilevel(Integer idEmployeeMultilevel) {
        return createEntityCriteria().add(Restrictions.eq("idEmployeeMultilevel", idEmployeeMultilevel)).list();
    }

    @Override
    public List<MultilevelEmployee> FindAllActives() {
        return createEntityCriteria().add(Restrictions.eq("status",true)).list();
    }

    @Override
    public MultilevelEmployee findByEmployee(Integer idEmployee) {
        return (MultilevelEmployee) createEntityCriteria().add(Restrictions.eq("idEmployee",idEmployee)).uniqueResult();
    }
}
