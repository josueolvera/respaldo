package mx.bidg.service;

import mx.bidg.model.CFolios;
import mx.bidg.model.CTables;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 2/12/15.
 */
public interface FoliosService {
    String createNew(CTables table);
    CFolios findByFolio(String folio);
    List<CFolios> findAll();
}
