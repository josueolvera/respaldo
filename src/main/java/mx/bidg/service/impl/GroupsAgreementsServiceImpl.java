/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.GroupsAgreementsDao;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.service.GroupsAgreementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupsAgreementsServiceImpl implements GroupsAgreementsService {
    
    @Autowired
    GroupsAgreementsDao groupsAgreementsDao;

    @Override
    public GroupsAgreements save(GroupsAgreements groupsAgreements) {
        return groupsAgreementsDao.save(groupsAgreements);
    }

    @Override
    public GroupsAgreements update(GroupsAgreements groupsAgreements) {
        return groupsAgreementsDao.update(groupsAgreements);
    }

    @Override
    public GroupsAgreements findById(Integer idGa) {
        return groupsAgreementsDao.findById(idGa);
    }

    @Override
    public List<GroupsAgreements> findAll() {
        return groupsAgreementsDao.findAll();
    }

    @Override
    public boolean delete(GroupsAgreements groupsAgreements) {
        groupsAgreementsDao.delete(groupsAgreements);
        return true;
    }
    
}
