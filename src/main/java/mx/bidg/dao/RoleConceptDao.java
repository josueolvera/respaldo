package mx.bidg.dao;

import mx.bidg.model.RoleConcept;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface RoleConceptDao extends InterfaceDao<RoleConcept> {
    List<RoleConcept> findByIdRoleAndIdTravelType(Integer idRole, Integer idTravelType);
}
