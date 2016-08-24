package mx.bidg.service;

import mx.bidg.model.CActionTypes;

import java.util.List;

/**
 * Created by josueolvera on 23/08/16.
 */
public interface CActionTypesService {
    CActionTypes save (CActionTypes actionTypes);
    CActionTypes update (CActionTypes actionTypes);
    CActionTypes findById (Integer idActionType);
    List<CActionTypes> findAll ();
    boolean delete(CActionTypes cActionTypes);
}
