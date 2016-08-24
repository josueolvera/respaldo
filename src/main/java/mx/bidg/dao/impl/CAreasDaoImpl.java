/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CAreasDao;
import mx.bidg.model.CAreas;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class CAreasDaoImpl extends AbstractDao<Integer, CAreas> implements CAreasDao {

    @Override
    public CAreas save(CAreas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CAreas findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CAreas> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CAreas>) criteria.list();
    }

    @Override
    public CAreas update(CAreas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CAreas entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CAreas findAreaWithRoles(Integer idArea) {
        Criteria criteria = createEntityCriteria();
        return (CAreas) criteria.add(Restrictions.idEq(idArea))
                .setFetchMode("roles", FetchMode.JOIN)
                .uniqueResult();
    }

    @Override
    public List<CAreas> findBySaemFlag(Integer idArea, Integer saemFlag) {
        Criteria criteria = createEntityCriteria();

        if (idArea != null){
            criteria.add(Restrictions.eq("idArea",idArea));
        }

        criteria.add(Restrictions.eq("saemFlag", saemFlag));

        return criteria.list();
    }
}
