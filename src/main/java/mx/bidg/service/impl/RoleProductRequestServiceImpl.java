package mx.bidg.service.impl;

import mx.bidg.dao.RoleProductRequestDao;
import mx.bidg.service.RoleProductRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import mx.bidg.dao.DistributorCostCenterDao;
import mx.bidg.model.DistributorCostCenter;
import mx.bidg.model.RoleProductRequest;

/**
 * Created by g_on_ on 22/05/2017.
 */
@Service
@Transactional
public class RoleProductRequestServiceImpl implements RoleProductRequestService{

    @Autowired
    private RoleProductRequestDao roleProductRequestDao;

    @Autowired
    private DistributorCostCenterDao distributorCostCenterDao;

    @Override
    public List<Integer> getIdsProductsRequestByDistributorCostCenter(Integer idDistributorCostCenter){
        return roleProductRequestDao.getIdsProductsRequestByDistributorCostCenter(idDistributorCostCenter);
    }

    @Override
    public List<RoleProductRequest> getProductsRequestByCostCenterAndAA(Integer idCostCenter, Integer idAccountingAccounts) {
        DistributorCostCenter distributorCostCenter = distributorCostCenterDao.findByIdCostCenterAndAA(idCostCenter, idAccountingAccounts);

        List<RoleProductRequest> roleProductRequests = roleProductRequestDao.findByDistributorCostCenter(distributorCostCenter.getIdDistributorCostCenter());

        return roleProductRequests;
    }
}
