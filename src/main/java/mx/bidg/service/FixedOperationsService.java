package mx.bidg.service;

import mx.bidg.model.FixedOperations;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface FixedOperationsService {
    FixedOperations save (FixedOperations fixedOperations);
    FixedOperations update (FixedOperations fixedOperations);
    FixedOperations findById(Integer idFixedOperations);
    boolean delete(FixedOperations fixedOperations);
    List<FixedOperations> findAll();
}
