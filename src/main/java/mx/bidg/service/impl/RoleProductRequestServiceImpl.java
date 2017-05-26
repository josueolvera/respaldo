package mx.bidg.service.impl;

import mx.bidg.dao.RoleProductRequestDao;
import mx.bidg.service.RoleProductRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
@Service
@Transactional
public class RoleProductRequestServiceImpl implements RoleProductRequestService{

    @Autowired
    private RoleProductRequestDao roleProductRequestDao;

    @Override
    public List<Integer> getIdsProductsRequestByDistributorCostCenter(Integer idDistributorCostCenter){
        return roleProductRequestDao.getIdsProductsRequestByDistributorCostCenter(idDistributorCostCenter);
    }
}
