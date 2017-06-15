package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestsDatesDao;
import mx.bidg.model.RequestsDates;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jcesar on 12/06/2017.
 */
@Repository
public class RequestsDatesDaoImpl extends AbstractDao<Integer, RequestsDates> implements RequestsDatesDao {

    @Override
    public RequestsDates findByIdRequestsDates(Integer idResquestDates) {
        return (RequestsDates)
                createEntityCriteria().add(Restrictions.eq("idRequestsDates", idResquestDates)).uniqueResult();
    }

    @Override
    public RequestsDates save(RequestsDates entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestsDates findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestsDates> findAll() {
        return createEntityCriteria().add(Restrictions.ne("idRequestsDates",0)).list();
    }

    @Override
    public RequestsDates update(RequestsDates entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestsDates entity) {
        delete(entity);
        return true;
    }
}
