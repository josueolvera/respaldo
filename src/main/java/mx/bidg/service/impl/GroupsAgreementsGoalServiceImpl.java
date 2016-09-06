package mx.bidg.service.impl;

import mx.bidg.dao.GroupsAgreementsGoalDao;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.model.GroupsAgreementsGoal;
import mx.bidg.service.GroupsAgreementsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class GroupsAgreementsGoalServiceImpl implements GroupsAgreementsGoalService {

    @Autowired
    GroupsAgreementsGoalDao groupsAgreementsGoalDao;

    @Override
    public GroupsAgreementsGoal save(GroupsAgreementsGoal agreementsGoal) {
        return groupsAgreementsGoalDao.save(agreementsGoal);
    }

    @Override
    public GroupsAgreementsGoal update(GroupsAgreementsGoal groupsAgreementsGoal) {
        return groupsAgreementsGoalDao.update(groupsAgreementsGoal);
    }

    @Override
    public GroupsAgreementsGoal findById(Integer idGroupsAgreementsGoal) {
        return groupsAgreementsGoalDao.findById(idGroupsAgreementsGoal);
    }

    @Override
    public boolean delete(GroupsAgreementsGoal agreementsGoal) {
        groupsAgreementsGoalDao.delete(agreementsGoal);
        return true;
    }

    @Override
    public List<GroupsAgreementsGoal> findAll() {
        return groupsAgreementsGoalDao.findAll();
    }
}
