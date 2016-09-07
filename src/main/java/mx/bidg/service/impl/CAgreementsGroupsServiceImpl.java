/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CAgreementsGroupsDao;
import mx.bidg.dao.RolesGroupAgreementsDao;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.RolesGroupAgreements;
import mx.bidg.service.CAgreementsGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CAgreementsGroupsServiceImpl implements CAgreementsGroupsService {
    
    @Autowired
    CAgreementsGroupsDao agreementsGroupsDao;

    @Autowired
    RolesGroupAgreementsDao rolesGroupAgreementsDao;

    @Override
    public CAgreementsGroups save(CAgreementsGroups agreementsGroups) {
        return agreementsGroupsDao.save(agreementsGroups);
    }

    @Override
    public CAgreementsGroups update(CAgreementsGroups agreementsGroups) {
        return agreementsGroupsDao.update(agreementsGroups);
    }

    @Override
    public boolean delete(CAgreementsGroups agreementsGroups) {
        agreementsGroupsDao.delete(agreementsGroups);
        return true;
    }

    @Override
    public CAgreementsGroups findById(Integer idAG) {
        return agreementsGroupsDao.findById(idAG);
    }

    @Override
    public List<CAgreementsGroups> findAll() {
        return agreementsGroupsDao.findAll();
    }

    @Override
    public CAgreementsGroups lowGroup(Integer idAG) {
        CAgreementsGroups agreementsGroups = agreementsGroupsDao.findById(idAG);
        agreementsGroups.setStatus(0);
        agreementsGroups = agreementsGroupsDao.update(agreementsGroups);

        List<RolesGroupAgreements> rolesGroupAgreementsList = rolesGroupAgreementsDao.findByGroup(idAG);

        for (RolesGroupAgreements rolesGroupAgreements : rolesGroupAgreementsList){
            rolesGroupAgreements.setHasGroup(false);
            rolesGroupAgreementsDao.update(rolesGroupAgreements);
        }

        return agreementsGroups;
    }

    @Override
    public List<CAgreementsGroups> findGroupsActives() {
        return agreementsGroupsDao.findGruoupActives();
    }

}
