package mx.bidg.service.impl;

import mx.bidg.dao.GroupsAgreementsCalculationDao;
import mx.bidg.model.GroupsAgreementsCalculation;
import mx.bidg.service.GroupsAgreementsCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class GroupsAgreementsCalculationServiceImpl implements GroupsAgreementsCalculationService {

    @Autowired
    GroupsAgreementsCalculationDao groupsAgreementsCalculationDao;

    @Override
    public GroupsAgreementsCalculation save(GroupsAgreementsCalculation groupsAgreementsCalculation) {
        return groupsAgreementsCalculationDao.save(groupsAgreementsCalculation);
    }

    @Override
    public GroupsAgreementsCalculation update(GroupsAgreementsCalculation groupsAgreementsCalculation) {
        return groupsAgreementsCalculationDao.update(groupsAgreementsCalculation);
    }

    @Override
    public GroupsAgreementsCalculation findById(Integer idGroupsAgreementsCalculation) {
        return groupsAgreementsCalculationDao.findById(idGroupsAgreementsCalculation);
    }

    @Override
    public boolean delete(GroupsAgreementsCalculation groupsAgreementsCalculation) {
        groupsAgreementsCalculationDao.delete(groupsAgreementsCalculation);
        return true;
    }

    @Override
    public List<GroupsAgreementsCalculation> findAll() {
        return groupsAgreementsCalculationDao.findAll();
    }
}
