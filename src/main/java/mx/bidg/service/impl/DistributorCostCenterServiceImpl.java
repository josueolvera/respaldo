package mx.bidg.service.impl;

import java.util.ArrayList;
import mx.bidg.dao.DistributorCostCenterDao;
import mx.bidg.model.DistributorCostCenter;
import mx.bidg.service.DistributorCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import mx.bidg.dao.AccountingAccountsDao;
import mx.bidg.model.AccountingAccounts;

/**
 * Created by Kevin Salvador on 16/03/2017.
 */
@Service
@Transactional
public class DistributorCostCenterServiceImpl implements DistributorCostCenterService{

    @Autowired
    private DistributorCostCenterDao distributorCostCenterDao;

    @Autowired
    private AccountingAccountsDao accountingAccountsDao;

    @Override
    public List<DistributorCostCenter> findAll() {
        return distributorCostCenterDao.findAll();
    }

    @Override
    public DistributorCostCenter findById(Integer id) {
        return distributorCostCenterDao.findById(id);
    }

    @Override
    public DistributorCostCenter save(DistributorCostCenter distributorCostCenter) {
        return distributorCostCenterDao.save(distributorCostCenter);
    }

    @Override
    public DistributorCostCenter update(DistributorCostCenter distributorCostCenter) {
        return distributorCostCenterDao.update(distributorCostCenter);
    }

    @Override
    public boolean delete(DistributorCostCenter distributorCostCenter) {
        return distributorCostCenterDao.delete(distributorCostCenter);
    }

    @Override
    public List<DistributorCostCenter> findByCostCenter(Integer idCostCenter) {
        return distributorCostCenterDao.findByCostCenter(idCostCenter);
    }

    @Override
    public DistributorCostCenter findByIdCostCenter(Integer idCostCenter) {
        return distributorCostCenterDao.findByIdCostCenter(idCostCenter);
    }

    @Override
    public List<DistributorCostCenter> findByIdBussinessAndDistributorAndCostCenter(Integer idBussinessLine,  Integer idDistributor,Integer idCostCenter) {
        return distributorCostCenterDao.findByIdBussinessAndDistributorAndCostCenter(idBussinessLine,idDistributor,idCostCenter);
    }

    @Override
    public List<Integer> getIdsDistributorByBusinessLine(Integer idBusinessLine) {
        return distributorCostCenterDao.getIdsDistributorsByBusinessLine(idBusinessLine);
    }

    @Override
    public List<Integer> getIdsCostCentersByBDistributor(Integer idDistributor, List<Integer> idsBussinessLines) {
        return distributorCostCenterDao.getIdsCostCentersByBDistributor(idDistributor, idsBussinessLines);
    }

    @Override
    public List<DistributorCostCenter> getAllByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC) {
        return distributorCostCenterDao.getAllByBusinessLineDistributorCC(idsBussinessLines, idsDistributors, idsCC);
    }

    @Override
    public List<Integer> getIdsCostCentersByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC) {
        return distributorCostCenterDao.getIdsCostCentersByBusinessLineDistributorCC(idsBussinessLines, idsDistributors, idsCC);
    }

    @Override
    public List<AccountingAccounts> getIdsAccountingAccountsByCostCenter(Integer idCostCenter){

        List<Integer> idAccountingAccounts = distributorCostCenterDao.getIdsAccountingAccountsByCostCenter(idCostCenter);


        List<AccountingAccounts> accountingAccountsList = new ArrayList<>();

        for (Integer idAccountingAccount : idAccountingAccounts){
            AccountingAccounts accountingAccount = accountingAccountsDao.findById(idAccountingAccount);
            if(accountingAccount.getIdBudgetSubcategory() != 0 && accountingAccount.getIdBudgetSubSubcategories() == 0){
                if(!accountingAccountsList.contains(accountingAccount)){
                    accountingAccountsList.add(accountingAccount);
                }
            }
        }

        return accountingAccountsList;
    }

    @Override
    public  List<Integer> getIdsDCCByDistributor(Integer idDistributor){
        return distributorCostCenterDao.getIdsDCCByDistributor(idDistributor);
    }

    @Override
    public DistributorCostCenter findByCostCenterAndAA(Integer idCostCenter, Integer idAccountingAccounts) {
        return distributorCostCenterDao.findByIdCostCenterAndAA(idCostCenter, idAccountingAccounts);
    }
}
