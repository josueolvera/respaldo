package mx.bidg.service.impl;

import mx.bidg.dao.RoleConceptDao;
import mx.bidg.model.RoleConcept;
import mx.bidg.service.RoleConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Repository
@Transactional
public class RoleConceptServiceImpl implements RoleConceptService {

    @Autowired
    RoleConceptDao roleConceptDao;

    @Override
    public RoleConcept save(RoleConcept roleConcept) {
        roleConceptDao.save(roleConcept);
        return roleConcept;
    }

    @Override
    public RoleConcept update(RoleConcept roleConcept) {
        roleConceptDao.update(roleConcept);
        return roleConcept;
    }

    @Override
    public RoleConcept findById(Integer idRoleConcept) {
        return roleConceptDao.findById(idRoleConcept);
    }

    @Override
    public List<RoleConcept> findAll() {
        return roleConceptDao.findAll();
    }

    @Override
    public List<RoleConcept> findByIdRoleAndIdTravelType(Integer idRole, Integer idTravelType) {
        return roleConceptDao.findByIdRoleAndIdTravelType(idRole, idTravelType);
    }

    @Override
    public Boolean delete(RoleConcept roleConcept) {
        roleConceptDao.delete(roleConcept);
        return true;
    }
}
