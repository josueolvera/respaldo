package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CMunicipiosDao;
import mx.bidg.model.CEstados;
import mx.bidg.model.CMunicipios;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Repository
public class CMunicipiosDaoImpl extends AbstractDao<Integer,CMunicipios> implements CMunicipiosDao {

    @Override
    public CMunicipios save(CMunicipios entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CMunicipios findById(int id) {
        return (CMunicipios) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CMunicipios> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CMunicipios>) criteria
                .addOrder(Order.asc("nombreMunicipios"))
                .list();
    }

    @Override
    public CMunicipios update(CMunicipios entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CMunicipios entity) {
        remove(entity);
        return true;
    }

    @Override
    public CMunicipios findMunicipio(CEstados e, int idMunicipios) {
        Criteria criteria = createEntityCriteria();
        return (CMunicipios) criteria
                .add(Restrictions.eq("estados",e))
                .add(Restrictions.eq("idMunicipio",idMunicipios))
                .uniqueResult();
    }
}
