/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.model.Users;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class UsersDaoImpl extends AbstractDao<Integer, Users> implements UsersDao {

    @Override
    public Users save(Users entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Users> findAll() {
        return (List<Users>) createEntityCriteria().list();
    }

    @Override
    public Users update(Users entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public boolean delete(Users entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users findByUsername(String username) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("username", username))
                .setFetchMode("usersRoleList", FetchMode.JOIN)
                .setFetchMode("usersRoleList.idSystemRole", FetchMode.JOIN)
                .setFetchMode("dwEmployee", FetchMode.JOIN)
                .setFetchMode("dwEmployee.dwEnterprise", FetchMode.JOIN);
        return (Users) criteria.uniqueResult();
    }

    @Override
    public Users findByIdFetchDwEmployee(int id) {
        Criteria criteria = createEntityCriteria()
                .add(Restrictions.idEq(id))
                .setFetchMode("dwEmployee", FetchMode.JOIN)
                .setFetchMode("dwEmployee.employee", FetchMode.JOIN)
                .setFetchMode("dwEmployee.dwEnterprise", FetchMode.JOIN)
                .setFetchMode("dwEmployee.role", FetchMode.JOIN);
        return (Users) criteria.uniqueResult();
    }
    
}
