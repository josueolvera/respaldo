package mx.bidg.service;

import mx.bidg.model.ApportionmentRole;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface ApportionmentRoleService {

    ApportionmentRole save(ApportionmentRole apportionmentRole);
    ApportionmentRole update(ApportionmentRole ApportionmentRole);
    ApportionmentRole findById(Integer idApportionmentRole);
    List<ApportionmentRole> findAll();
    boolean delete(ApportionmentRole apportionmentRole);
}
