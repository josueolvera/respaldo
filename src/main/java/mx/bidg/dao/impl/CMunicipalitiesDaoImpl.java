package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CMunicipalitiesDao;
import mx.bidg.model.CMunicipalities;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Repository
public class CMunicipalitiesDaoImpl extends AbstractDao<Integer,CMunicipalities> implements CMunicipalitiesDao {
    @Override
    public CMunicipalities save(CMunicipalities entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CMunicipalities findById(int id) {
        return (CMunicipalities) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CMunicipalities> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CMunicipalities>) criteria
                .addOrder(Order.asc("municipalityName"))
                .list();
    }

    @Override
    public CMunicipalities update(CMunicipalities entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CMunicipalities entity) {
        remove(entity);
        return true;
    }
}
