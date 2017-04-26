/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRegionsDao;
import mx.bidg.model.CRegions;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CRegionsDaoImpl extends AbstractDao<Integer, CRegions> implements CRegionsDao {

    @Override
    public CRegions findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRegions> findAll() {
        return (List<CRegions>) createEntityCriteria().add(Restrictions.ne("idRegion", 0)).list();
        
    }

    @Override
    public CRegions save(CRegions entity) {
        persist(entity);
        return entity;
    }
    @Override
    public CRegions update(CRegions entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CRegions entity) {
        delete(entity);
        return true;
    }

    @Override
    public List<CRegions> findRegionsBySaemFlag(Integer idRegion ,Integer saemFlag) {
        Criteria criteria = createEntityCriteria();

        if (idRegion != null){
            criteria.add(Restrictions.eq("idRegion",idRegion));
        }

        criteria.add(Restrictions.eq("saemFlag", saemFlag)).add(Restrictions.ne("idRegion", 0));

        return criteria.list();
    }
}
