package mx.bidg.dao;

import mx.bidg.model.CZonas;
import mx.bidg.model.DistributorAreaRol;

import java.util.List;

/**
 * Created by gerardo8 on 15/08/16.
 */
public interface DistributorAreaRolDao extends InterfaceDao <DistributorAreaRol> {
    List<DistributorAreaRol> findRolByDistributorArea (Integer idDistributor, Integer idArea);
    List<DistributorAreaRol> findRolByArea(Integer idArea);
}
