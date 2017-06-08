package mx.bidg.service;

import java.util.List;
import mx.bidg.model.RoleProductRequest;

/**
 * Created by g_on_ on 22/05/2017.
 */
public interface RoleProductRequestService {

    List<Integer> getIdsProductsRequestByDistributorCostCenter(Integer idCostCenter);
    List<RoleProductRequest> getProductsRequestByCostCenterAndAA(Integer idCostCenter, Integer idAccountingAccounts);
}
