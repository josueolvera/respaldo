package mx.bidg.service.impl;

import mx.bidg.dao.CRolesDao;
import mx.bidg.model.CRoles;
import mx.bidg.service.CRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * Created by jolvera on 24/06/16.
 */
@Service
@Transactional
public class CRolesServiceImpl implements CRolesService {

    @Autowired
    CRolesDao cRolesDao;

    @Override
    public List<CRoles> findAll() {
        return cRolesDao.findAll();
    }

    @Override
    public CRoles findById(Integer idRol) {
        return cRolesDao.findById(idRol);
    }

    @Override
    public CRoles save(CRoles cRoles) {
        return cRolesDao.save(cRoles);
    }

    @Override
    public CRoles update(CRoles cRoles) {
        return cRolesDao.update(cRoles);
    }

    @Override
    public boolean delete(CRoles cRoles) {
        return cRolesDao.delete(cRoles);
    }

}
