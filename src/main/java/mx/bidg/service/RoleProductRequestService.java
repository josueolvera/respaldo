package mx.bidg.service;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
public interface RoleProductRequestService {

    List<Integer> getIdsProductsRequestByCostCenter(Integer idCostCenter);
}
