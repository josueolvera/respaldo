package mx.bidg.dao;

import mx.bidg.model.RolesGroupAgreements;
import mx.bidg.model.RulesCalculation;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface RolesGroupAgreementsDao extends InterfaceDao<RolesGroupAgreements> {
    List<RolesGroupAgreements> findByRole(Integer idRole);
}
