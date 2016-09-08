package mx.bidg.service;

import mx.bidg.model.GroupsAgreementsGoal;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface GroupsAgreementsGoalService {
    GroupsAgreementsGoal save(GroupsAgreementsGoal agreementsGoal);
    GroupsAgreementsGoal update(GroupsAgreementsGoal groupsAgreementsGoal);
    GroupsAgreementsGoal findById(Integer idGroupsAgreementsGoal);
    boolean delete(GroupsAgreementsGoal agreementsGoal);
    List<GroupsAgreementsGoal> findAll();
}
