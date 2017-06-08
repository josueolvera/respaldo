/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestProductsDao;
import mx.bidg.model.RequestProducts;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class RequestProductsDaoImpl extends AbstractDao<Integer, RequestProducts> implements RequestProductsDao {

    @Override
    public RequestProducts save(RequestProducts entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestProducts findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestProducts> findAll() {
        return  createEntityCriteria().list();
    }

    @Override
    public RequestProducts update(RequestProducts entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestProducts entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<RequestProducts> findByIdRequest(Integer idRequest) {
        return createEntityCriteria().add(Restrictions.eq("idRequest", idRequest))
                .setFetchMode("request", FetchMode.JOIN)
                .setFetchMode("roleProductRequest", FetchMode.JOIN)
                .list()
                ;
    }
}
