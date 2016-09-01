/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAgreementsGroupsDao;
import mx.bidg.model.CAgreementsGroups;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CAgreementsGroupsDaoImpl extends AbstractDao<Integer, CAgreementsGroups> implements CAgreementsGroupsDao {

    @Override
    public CAgreementsGroups save(CAgreementsGroups entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CAgreementsGroups findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAgreementsGroups> findAll() {
        return (List<CAgreementsGroups>) createEntityCriteria().list();
    }

    @Override
    public CAgreementsGroups update(CAgreementsGroups entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CAgreementsGroups entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CAgreementsGroups> findGruoupActives() {
        return createEntityCriteria().add(Restrictions.eq("status",1)).list();
    }
}
