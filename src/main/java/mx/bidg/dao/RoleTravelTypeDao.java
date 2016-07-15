package mx.bidg.dao;

import mx.bidg.model.RoleTravelType;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface RoleTravelTypeDao extends InterfaceDao<RoleTravelType> {
    List<RoleTravelType> findByIdRole(Integer idRole);
}
