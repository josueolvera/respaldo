/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.UsersRoleDao;
import mx.bidg.model.Users;
import mx.bidg.model.UsersRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class UsersRoleDaoImpl extends AbstractDao<Integer, UsersRole> implements UsersRoleDao {

    @Override
    public List<UsersRole> findAllByUserId(Users idUser) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("idUser", idUser));
        return (List<UsersRole>) criteria.list();
    }

    @Override
    public UsersRole save(UsersRole entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsersRole findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsersRole> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsersRole update(UsersRole entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(UsersRole entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
