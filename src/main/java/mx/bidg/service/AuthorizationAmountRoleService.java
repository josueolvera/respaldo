package mx.bidg.service;

import mx.bidg.model.AuthorizationAmountRole;

import java.util.List;

/**
 * Created by desarrollador on 10/06/17.
 */
public interface AuthorizationAmountRoleService {
    AuthorizationAmountRole save(AuthorizationAmountRole authorizationAmountRole);
    AuthorizationAmountRole update(AuthorizationAmountRole authorizationAmountRole);
    AuthorizationAmountRole findById(Integer idAuthorizationAmountRole);
    List<AuthorizationAmountRole> findAll();
    boolean delete(AuthorizationAmountRole authorizationAmountRole);
}
