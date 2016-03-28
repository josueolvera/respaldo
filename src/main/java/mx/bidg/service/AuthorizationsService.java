package mx.bidg.service;

import mx.bidg.model.Authorizations;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
public interface AuthorizationsService {
    Authorizations findById(int id);
    List<Authorizations> findAll();
    Authorizations save(Authorizations entity);
    Authorizations update(Authorizations entity);
    Authorizations authorize(Authorizations authorizations);
    Authorizations reject(Authorizations authorizations);
    Long countByFolio(String folio);
}
