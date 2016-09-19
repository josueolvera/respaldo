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
    public List<AgreementsGroupCondition> conditions(Integer idAg) {
        return agreementsGroupConditionDao.conditionList(idAg);
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
    
}
