package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.Authorizations;
import mx.bidg.model.CAuthorizationStatus;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
public interface AuthorizationsDao extends InterfaceDao<Authorizations> {
    
    List<Authorizations> findByFolio(String folio);
    List<Authorizations> findByFolioAndStatus(String folio, CAuthorizationStatus status);
    Long countByFolio(String folio);
    
}
