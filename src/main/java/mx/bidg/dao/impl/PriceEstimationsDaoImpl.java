/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.PriceEstimationsDao;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.Requests;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PriceEstimationsDaoImpl extends AbstractDao<Integer, PriceEstimations> implements PriceEstimationsDao {

    @Override
    public PriceEstimations save(PriceEstimations entity) {
        persist(entity);
        return entity;
    }

    @Override
    public PriceEstimations findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<PriceEstimations> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PriceEstimations update(PriceEstimations entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(PriceEstimations entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PriceEstimations> findByIdRequest(int idRequest) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.eq("request", new Requests(idRequest)))
                .setFetchMode("request", FetchMode.JOIN);
        return (List<PriceEstimations>) criteria.list();
    }

    @Override
    public PriceEstimations findByIdFetchRequestStatus(int idEstimation) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(idEstimation))
                .setFetchMode("request", FetchMode.JOIN)
                .setFetchMode("request.requestStatus", FetchMode.JOIN);
        return (PriceEstimations) criteria.uniqueResult();
    }
    
}
