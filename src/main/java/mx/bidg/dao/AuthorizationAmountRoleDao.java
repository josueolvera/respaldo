package mx.bidg.dao;

import mx.bidg.model.AuthorizationAmountRole;

/**
 * Created by desarrollador on 10/06/17.
 */
public interface AuthorizationAmountRoleDao extends InterfaceDao<AuthorizationAmountRole> {
    AuthorizationAmountRole findByRole(Integer idRole);
}
