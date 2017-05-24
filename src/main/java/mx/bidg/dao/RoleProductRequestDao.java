package mx.bidg.dao;

import mx.bidg.model.RoleProductRequest;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
public interface RoleProductRequestDao extends InterfaceDao<RoleProductRequest>{
    List<Integer> getIdsProductsRequestByCostCenter(Integer idCostCenter);
}
