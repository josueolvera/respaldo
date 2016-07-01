package mx.bidg.dao;

import mx.bidg.model.CEmployeeDocumentsTypes;

import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
public interface CEmployeeDocumentsTypesDao extends InterfaceDao<CEmployeeDocumentsTypes> {
    List<CEmployeeDocumentsTypes> findByInputField();
}
