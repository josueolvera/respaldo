package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.Authorizations;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
public interface AuthorizationsDao extends InterfaceDao<Authorizations> {
    
    List<Authorizations> findByFolio(String folio);
    Long countByFolio(String folio);
    
}
