package mx.bidg.dao;

import mx.bidg.model.CRoles;

/**
 * Created by gerardo8 on 24/06/16.
 */
public interface CRolesDao extends InterfaceDao<CRoles> {
    CRoles findByIdRole(Integer idRole);
}
