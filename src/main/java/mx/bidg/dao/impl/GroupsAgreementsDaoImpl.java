/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.LinkedList;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.GroupsAgreementsDao;
import mx.bidg.model.CAgreements;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.GroupsAgreements;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class GroupsAgreementsDaoImpl extends AbstractDao<Integer, GroupsAgreements> implements GroupsAgreementsDao {

    @Override
    public GroupsAgreements save(GroupsAgreements entity) {
        persist(entity);
        return entity;
    }

    @Override
    public GroupsAgreements findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<GroupsAgreements> findAll() {
        return (List<GroupsAgreements>) createEntityCriteria().list();
    }

    @Override
    public GroupsAgreements update(GroupsAgreements entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(GroupsAgreements entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<GroupsAgreements> findGroupsAgreementsActives(List<CAgreementsGroups> groupsList, List<CAgreements> agreementsList) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionAgreements = Restrictions.disjunction();
        Disjunction disjunctionGroups = Restrictions.disjunction();

        if (!agreementsList.isEmpty()){
            for (CAgreements agreement : agreementsList){
                disjunctionAgreements.add(Restrictions.eq("idAgreement", agreement.getIdAgreement()));
            }
            criteria.add(disjunctionAgreements);
        }

        if (!groupsList.isEmpty()){
            for (CAgreementsGroups group : groupsList){
                disjunctionGroups.add(Restrictions.eq("idAg", group.getIdAg()));
            }
            criteria.add(disjunctionGroups);
        }

        return (List<GroupsAgreements>) criteria.add(Restrictions.eq("hasAgreement",true)).list();
    }

    @Override
    public List<GroupsAgreements> findGroupsAgreementsByAg(Integer idAg) {
        return (List<GroupsAgreements>) createEntityCriteria().add(Restrictions.eq("idAg", idAg))
                .addOrder(Order.asc("idAgreement")).list();
    }

    @Override
    public List<GroupsAgreements> findGroupsAgreementsSelectedByAg(Integer idAg) {
        return (List<GroupsAgreements>) createEntityCriteria()
                .add(Restrictions.eq("idAg", idAg)).add(Restrictions.eq("hasAgreement",true))
                .addOrder(Order.asc("idAgreement")).list();
    }
}
