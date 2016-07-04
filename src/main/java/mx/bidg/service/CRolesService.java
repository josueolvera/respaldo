package mx.bidg.service;

import mx.bidg.model.CRoles;

import java.util.List;

/**
 * Created by jolvera on 24/06/16.
 */
public interface CRolesService {

    List<CRoles> findAll();
    CRoles findById(Integer idRol);
}
