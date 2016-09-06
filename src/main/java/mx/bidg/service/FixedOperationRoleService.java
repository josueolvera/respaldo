package mx.bidg.service;

import mx.bidg.model.FixedOperationRole;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface FixedOperationRoleService {
    FixedOperationRole save(FixedOperationRole fixedOperationRole);
    FixedOperationRole update(FixedOperationRole fixedOperationRole);
    FixedOperationRole findById (Integer idFixedOperationRole);
    boolean delete (FixedOperationRole fixedOperationRole);
    List<FixedOperationRole> findAll();
}
