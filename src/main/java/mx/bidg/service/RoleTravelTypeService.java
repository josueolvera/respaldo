package mx.bidg.service;

import mx.bidg.model.RoleTravelType;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface RoleTravelTypeService {
    RoleTravelType save (RoleTravelType roleTravelType);
    RoleTravelType update (RoleTravelType roleTravelType);
    RoleTravelType findById(Integer idRoleTravelType);
    List<RoleTravelType> findAll();
    Boolean delete(RoleTravelType roleTravelType);
}
