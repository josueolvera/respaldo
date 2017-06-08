package mx.bidg.service;

import mx.bidg.model.CRequestTypes;

import java.util.List;

/**
 * Created by desarrollador on 2/06/17.
 */
public interface CRequestTypesService {
    CRequestTypes findById(Integer idCRequestTypes);
    CRequestTypes save(CRequestTypes cRequestTypes);
    CRequestTypes update(CRequestTypes cRequestTypes);
    List<CRequestTypes> findAll();
    boolean delete(CRequestTypes cRequestTypes);
}
