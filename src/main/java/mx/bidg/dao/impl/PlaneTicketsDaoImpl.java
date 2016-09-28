package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PlaneTicketsDao;
import mx.bidg.model.PlaneTickets;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class PlaneTicketsDaoImpl extends AbstractDao<Integer, PlaneTickets> implements PlaneTicketsDao {
    @Override
    public PlaneTickets save(PlaneTickets entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PlaneTickets findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PlaneTickets> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public PlaneTickets update(PlaneTickets entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PlaneTickets entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<PlaneTickets> getPlaneTickets(Integer idUser) {
        Criteria criteria = createEntityCriteria();

        if (idUser != null) {
            criteria.createCriteria("request")
                    .add(Restrictions.eq("idUserRequest", idUser));
        }

        return criteria.list();
    }
}
