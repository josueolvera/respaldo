package mx.bidg.service;

import mx.bidg.model.COperations;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface COperationsService {

    COperations save (COperations cOperations);
    COperations update(COperations cOperations);
    COperations findById (Integer idCOperations);
    boolean delete(COperations cOperations);
    List<COperations> findAll();
}
