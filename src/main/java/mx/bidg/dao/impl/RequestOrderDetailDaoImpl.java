package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestOrderDetailDao;
import mx.bidg.model.RequestOrderDetail;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by desarrollador on 11/06/17.
 */
@Repository
public class RequestOrderDetailDaoImpl extends AbstractDao<Integer, RequestOrderDetail> implements RequestOrderDetailDao {

    @Override
    public RequestOrderDetail save(RequestOrderDetail entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestOrderDetail findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestOrderDetail> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RequestOrderDetail update(RequestOrderDetail entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestOrderDetail entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<RequestOrderDetail> findByidReqOrderDocument(Integer idRequestOrderDocument) {
        return createEntityCriteria()
                .add(Restrictions.eq("idRequestOrderDocument", idRequestOrderDocument))
                .list();
    }
}
