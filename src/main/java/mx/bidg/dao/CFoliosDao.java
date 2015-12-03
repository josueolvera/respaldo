package mx.bidg.dao;

import mx.bidg.model.CFolios;

/**
 * @author Rafael Viveros
 * Created on 3/12/15.
 */
public interface CFoliosDao extends InterfaceDao<CFolios> {
    CFolios findByFolio(String folio);
}
