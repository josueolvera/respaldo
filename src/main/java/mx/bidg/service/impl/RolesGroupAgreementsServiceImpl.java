package mx.bidg.service.impl;

import mx.bidg.dao.RolesGroupAgreementsDao;
import mx.bidg.model.RolesGroupAgreements;
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
        return rolesGroupAgreementsDao.findByRole(idRole);
    }
}
