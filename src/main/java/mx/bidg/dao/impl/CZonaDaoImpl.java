package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CZonaDao;
import mx.bidg.model.CZonas;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
@Repository
public class CZonaDaoImpl extends AbstractDao <Integer, CZonas> implements CZonaDao {

    @Override
    public CZonas save(CZonas entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CZonas findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CZonas> findAll() {
        return (List<CZonas>) createEntityCriteria().add(Restrictions.ne("idZonas", 0)).list();
    }

    @Override
    public CZonas update(CZonas entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CZonas entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CZonas> findBySaemFlag(Integer idZonas, Integer saemFlag) {
        Criteria criteria = createEntityCriteria();

        if (idZonas != null){
            criteria.add(Restrictions.eq("idZonas",idZonas));
        }

        criteria.add(Restrictions.eq("saemFlag", saemFlag)).add(Restrictions.ne("idZonas", 0));

        return criteria.list();
    }
}
