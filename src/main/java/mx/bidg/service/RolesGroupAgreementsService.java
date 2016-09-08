package mx.bidg.service;

import mx.bidg.model.RolesGroupAgreements;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface RolesGroupAgreementsService {
    RolesGroupAgreements save(RolesGroupAgreements rolesGroupAgreements);
    RolesGroupAgreements update(RolesGroupAgreements rolesGroupAgreements);
    RolesGroupAgreements findById(Integer idRolesGroupAgreements);
    boolean delete(RolesGroupAgreements rolesGroupAgreements);
    List<RolesGroupAgreements> findAll();
    List<RolesGroupAgreements> findByRole(Integer idRole);
    List<RolesGroupAgreements> findByRoles(Integer idRole);
}
