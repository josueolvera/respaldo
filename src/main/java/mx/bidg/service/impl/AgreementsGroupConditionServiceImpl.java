package mx.bidg.service.impl;

import mx.bidg.dao.AgreementsGroupConditionDao;
import mx.bidg.dao.CommissionAmountGroupDao;
import mx.bidg.model.AgreementsGroupCondition;
import mx.bidg.model.CommissionAmountGroup;
import mx.bidg.service.AgreementsGroupConditionService;
import org.exolab.castor.xml.descriptors.ListClassDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
@Service
@Transactional
public class AgreementsGroupConditionServiceImpl implements AgreementsGroupConditionService {

    @Autowired
    AgreementsGroupConditionDao agreementsGroupConditionDao;

    @Autowired
    CommissionAmountGroupDao commissionAmountGroupDao;

    @Override
    public AgreementsGroupCondition save(AgreementsGroupCondition agreementsGroupCondition) {
        return agreementsGroupConditionDao.save(agreementsGroupCondition);
    }

    @Override
    public AgreementsGroupCondition update(AgreementsGroupCondition agreementsGroupCondition) {
        return agreementsGroupConditionDao.update(agreementsGroupCondition);
    }

    @Override
    public AgreementsGroupCondition findById(Integer idGroupCondition) {
        return agreementsGroupConditionDao.findById(idGroupCondition);
    }

    @Override
    public boolean delete(AgreementsGroupCondition agreementsGroupCondition) {
        agreementsGroupConditionDao.delete(agreementsGroupCondition);
        return true;
    }

    @Override
    public List<AgreementsGroupCondition> findAll() {
        return agreementsGroupConditionDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> setTabulator(List<AgreementsGroupCondition> agreementsGroupConditionList) {
        int conditionNumber = agreementsGroupConditionList.size();

        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
                AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            if (groupCondition.getTypeOperation() == 1){
                //1 significa que calcule la comision del monto comisionable
                List<CommissionAmountGroup> amountGroups = commissionAmountGroupDao.getComissionsByConditon(groupCondition);

                for (CommissionAmountGroup cAGroup :  amountGroups){
                    cAGroup.setTabulator(groupCondition.getTabulator());
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal comission = groupCondition.getTabulator().divide(divisor);
                    cAGroup.setCommission(cAGroup.getAmount().multiply(comission));
                    commissionAmountGroupDao.update(cAGroup);
                }
            } else if(groupCondition.getTypeOperation() == 2){
                //2 significa que calcule bono cumplimiento
                List<CommissionAmountGroup> requestGroups = commissionAmountGroupDao.getBonusByConditon(groupCondition);

                for (CommissionAmountGroup cAGroup :  requestGroups){
                    cAGroup.setTabulator(groupCondition.getAmountMin());
                    BigDecimal comission = groupCondition.getTabulator();
                    cAGroup.setCommission(comission);
                    commissionAmountGroupDao.update(cAGroup);
                }
            } else if (groupCondition.getTypeOperation() == 3){
                //3 signifique que calcule el alcance de la sucursal
                List<CommissionAmountGroup> scopeGroups = commissionAmountGroupDao.getScopeByConditon(groupCondition);

                for(CommissionAmountGroup commissionAmountGroup : scopeGroups){
                    commissionAmountGroup.setTabulator(groupCondition.getTabulator());
                    BigDecimal divide = new BigDecimal(100);
                    BigDecimal comission = groupCondition.getTabulator().divide(divide);
                    commissionAmountGroup.setCommission(commissionAmountGroup.getAmount().multiply(comission));
                    commissionAmountGroupDao.update(commissionAmountGroup);
                }
            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<AgreementsGroupCondition> conditions(Integer idAg, Integer idDateCalculation) {
        return agreementsGroupConditionDao.conditionList(idAg, idDateCalculation);
    }

    @Override
    public List<AgreementsGroupCondition> findByGroupCondition(Integer idAg) {
        return agreementsGroupConditionDao.listByAgreementGroup(idAg);
    }

    @Override
    public AgreementsGroupCondition updateStatus(Integer idGroupCondition, boolean statusBoolean) {
        return agreementsGroupConditionDao.updateStatus(idGroupCondition, statusBoolean);
    }

    @Override
    public AgreementsGroupCondition getFinalOrder(Integer idAg) {
        return agreementsGroupConditionDao.getFinalOrder(idAg);
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByGoalBranchToZona(List<AgreementsGroupCondition> agreementsGroupConditionList) {
        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            List<CommissionAmountGroup> zonasList = commissionAmountGroupDao.findByGroupZonalAndZona();

            for (CommissionAmountGroup commissionAmountGroup : zonasList){
                List<CommissionAmountGroup> commissionAmountGroupList = commissionAmountGroupDao.obtainBranchByZonaAndCondition(commissionAmountGroup.getIdZona(), groupCondition);
                if (commissionAmountGroupList.size() > 6){
                    BigDecimal defaultValue = new BigDecimal(6);
                    BigDecimal tabulator = groupCondition.getTabulator().multiply(defaultValue);
                    commissionAmountGroup.setTabulator(tabulator);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulator.divide(divisor);
                    BigDecimal comission = percentage.multiply(commissionAmountGroup.getAmount());
                    commissionAmountGroup.setCommission(comission);
                    commissionAmountGroupDao.update(commissionAmountGroup);
                }else {
                    int size = commissionAmountGroupList.size();
                    BigDecimal value = new BigDecimal(size);
                    BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
                    commissionAmountGroup.setTabulator(tabulatorBranch);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulatorBranch.divide(divisor);
                    BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroup.getAmount());
                    commissionAmountGroup.setCommission(commissionByBranch);
                    commissionAmountGroupDao.update(commissionAmountGroup);
                }
            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByGoalBranchToRegion(List<AgreementsGroupCondition>  agreementsGroupConditionList) {
        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            List<CommissionAmountGroup> regionList = commissionAmountGroupDao.findByGroupRegionslAndRegion();

            for (CommissionAmountGroup commissionAmountGroups : regionList){
                List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.obtainBranchByRegionAndCondition(commissionAmountGroups.getIdRegion(), groupCondition);
                if (branchsList.size() > 25){
                    BigDecimal defaultValue = new BigDecimal(25);
                    BigDecimal tabulator = groupCondition.getTabulator().multiply(defaultValue);
                    commissionAmountGroups.setTabulator(tabulator);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulator.divide(divisor);
                    BigDecimal comission = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(comission);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }else {
                    int size = branchsList.size();
                    BigDecimal value = new BigDecimal(size);
                    BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
                    commissionAmountGroups.setTabulator(tabulatorBranch);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulatorBranch.divide(divisor);
                    BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(commissionByBranch);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }
            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByGoalBranchToDistributor(List<AgreementsGroupCondition> agreementsGroupConditionList) {
        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            List<CommissionAmountGroup> distributorList = commissionAmountGroupDao.findByGroupComercialAndDistributor();

            for (CommissionAmountGroup commissionAmountGroups : distributorList){
                List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.obtainBranchByDistributorAndCondition(commissionAmountGroups.getIdDistributor(), groupCondition);
                if (branchsList.size() > 40){
                    BigDecimal defaultValue = new BigDecimal(40);
                    BigDecimal tabulator = groupCondition.getTabulator().multiply(defaultValue);
                    commissionAmountGroups.setTabulator(tabulator);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulator.divide(divisor);
                    BigDecimal comission = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(comission);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }else {
                    int size = branchsList.size();
                    BigDecimal value = new BigDecimal(size);
                    BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
                    commissionAmountGroups.setTabulator(tabulatorBranch);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulatorBranch.divide(divisor);
                    BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(commissionByBranch);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }
            }
        }

        return commissionAmountGroupDao.findAll();
    }

}
