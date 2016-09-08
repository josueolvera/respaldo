package mx.bidg.service.impl;

import mx.bidg.dao.CAgreementsGroupsDao;
import mx.bidg.dao.RolesGroupAgreementsDao;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.model.RolesGroupAgreements;
import mx.bidg.service.CAgreementsGroupsService;
import mx.bidg.service.RolesGroupAgreementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class RolesGroupAgreementsServiceImpl implements RolesGroupAgreementsService {

    @Autowired
    RolesGroupAgreementsDao rolesGroupAgreementsDao;

    @Autowired
    CAgreementsGroupsDao cAgreementsGroupsDao;

    @Override
    public RolesGroupAgreements save(RolesGroupAgreements rolesGroupAgreements) {
        return rolesGroupAgreementsDao.save(rolesGroupAgreements);
    }

    @Override
    public RolesGroupAgreements update(RolesGroupAgreements rolesGroupAgreements) {
        return rolesGroupAgreementsDao.update(rolesGroupAgreements);
    }

    @Override
    public RolesGroupAgreements findById(Integer idRolesGroupAgreements) {
        return rolesGroupAgreementsDao.findById(idRolesGroupAgreements);
    }

    @Override
    public boolean delete(RolesGroupAgreements rolesGroupAgreements) {
        rolesGroupAgreementsDao.delete(rolesGroupAgreements);
        return true;
    }

    @Override
    public List<RolesGroupAgreements> findAll() {
        return rolesGroupAgreementsDao.findAll();
    }

    @Override
    public List<RolesGroupAgreements> findByRole(Integer idRole) {
        List<CAgreementsGroups> agreementsGroupsList = cAgreementsGroupsDao.findGruoupActives();
        return rolesGroupAgreementsDao.findByRole(idRole, agreementsGroupsList);
    }

    @Override
    public List<RolesGroupAgreements> findByRoles(Integer idRole) {
        List<CAgreementsGroups> agreementsGroupsList = cAgreementsGroupsDao.findGruoupActives();
        return rolesGroupAgreementsDao.findByRoles(idRole, agreementsGroupsList);
    }
}
