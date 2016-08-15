package mx.bidg.service;

import mx.bidg.model.DistributorAreaRol;

import java.util.List;

/**
 * Created by gerardo8 on 15/08/16.
 */
public interface DistributorAreaRolService {
    List<DistributorAreaRol> findAll();
    DistributorAreaRol findById(Integer idDistributorAreaRol);
    DistributorAreaRol save(DistributorAreaRol distributorAreaRol);
    DistributorAreaRol update(DistributorAreaRol distributorAreaRol);
    Boolean delete(DistributorAreaRol distributorAreaRol);
}
