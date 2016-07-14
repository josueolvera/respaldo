package mx.bidg.service;

import mx.bidg.model.RoleConcept;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface RoleConceptService {
    RoleConcept save (RoleConcept roleConcept);
    RoleConcept update (RoleConcept roleConcept);
    RoleConcept findById (Integer idRoleConcept);
    List<RoleConcept> findAll();
    Boolean delete (RoleConcept roleConcept);
}
