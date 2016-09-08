package mx.bidg.service;

import mx.bidg.model.GroupsAgreementsCalculation;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
public interface GroupsAgreementsCalculationService {
    GroupsAgreementsCalculation save(GroupsAgreementsCalculation groupsAgreementsCalculation);
    GroupsAgreementsCalculation update (GroupsAgreementsCalculation groupsAgreementsCalculation);
    GroupsAgreementsCalculation findById (Integer idGroupsAgreementsCalculation);
    boolean delete(GroupsAgreementsCalculation groupsAgreementsCalculation);
    List<GroupsAgreementsCalculation> findAll ();
}
