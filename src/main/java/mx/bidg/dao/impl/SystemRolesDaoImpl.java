/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.io.Serializable;
import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.InterfaceDao;
import mx.bidg.dao.SystemRolesDao;
import mx.bidg.model.SystemRoles;

/**
 *
 * @author sistemask
 */
public class SystemRolesDaoImpl extends AbstractDao<Integer, SystemRoles> implements SystemRolesDao, InterfaceDao<SystemRoles> {

    @Override
    public SystemRoles findById(int id) {
        return getByKey(id);
    }

    @Override
    public SystemRoles save(SystemRoles entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SystemRoles> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SystemRoles update(SystemRoles entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(SystemRoles entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
