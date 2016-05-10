package mx.bidg.dao;

import mx.bidg.model.CStockDocumentsTypes;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 29/12/15.
 */
public interface CStockDocumentsTypesDao extends InterfaceDao<CStockDocumentsTypes> {

    List<CStockDocumentsTypes> findAllRequired();
    List<CStockDocumentsTypes> findAllNoRequired();
}
