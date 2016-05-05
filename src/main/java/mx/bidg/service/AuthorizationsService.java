package mx.bidg.service;

import mx.bidg.model.Authorizations;
import mx.bidg.model.CAuthorizationStatus;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
public interface AuthorizationsService {
    Authorizations findById(int id);
    List<Authorizations> findAll();
    List<Authorizations> findByFolioAndStatus(String folio, CAuthorizationStatus status);
    Authorizations save(Authorizations entity);
    Authorizations update(Authorizations entity);
    Authorizations authorize(Authorizations authorizations);
    Authorizations reject(Authorizations authorizations);
    Boolean deleteByFolio(String folio);
    Long countByFolio(String folio);
}
