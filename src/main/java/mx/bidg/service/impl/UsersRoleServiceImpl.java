/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.UsersRoleDao;
import mx.bidg.model.UsersRole;
import mx.bidg.service.UsersRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class UsersRoleServiceImpl implements UsersRoleService {
    
    @Autowired
    UsersRoleDao usersRoleDao;

    @Override
    public List<UsersRole> findAllByUserId(int idUser) {
        return usersRoleDao.findAllByUserId(idUser);
    }
    
}
