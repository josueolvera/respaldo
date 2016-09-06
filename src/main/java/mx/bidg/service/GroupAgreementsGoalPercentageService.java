package mx.bidg.service;

import mx.bidg.model.GroupAgreementsGoalPercentage;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface GroupAgreementsGoalPercentageService {

    GroupAgreementsGoalPercentage save(GroupAgreementsGoalPercentage groupAgreementsGoalPercentage);
    GroupAgreementsGoalPercentage update(GroupAgreementsGoalPercentage groupAgreementsGoalPercentage);
    GroupAgreementsGoalPercentage findById (Integer idGroupAgreementsGoalPercentage);
    boolean delete (GroupAgreementsGoalPercentage groupAgreementsGoalPercentage);
    List<GroupAgreementsGoalPercentage> findAll();
}
