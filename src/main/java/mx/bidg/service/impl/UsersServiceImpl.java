/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.UsersDao;
import mx.bidg.exceptions.ActiveSessionException;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.Users;
import mx.bidg.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    
    @Autowired
    UsersDao usersDao;

    @Override
    public Users findByUserName(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users verifyUserLogin(Users user) {
        
        Users userDB = usersDao.findByUsername(user.getUsername());
        
        if(userDB == null) {
            throw new ValidationException("El usuario no existe. Username: " + user.getUsername());
        }
        
        if(userDB.getActiveSession() == 1) {
            System.out.println("Aqui entro");
            throw new ActiveSessionException("El usuario ya tiene una sesion activa");
        }
        
        if(userDB.getPassword().equals(user.getPassword())) {
            userDB.setActiveSession(1);
            usersDao.update(userDB);
            return userDB;
        } else {
            throw new ValidationException("Contraseña incorrecta. Username: " + user.getUsername());
        }
    }

    @Override
    public boolean logout(Users user) {
        
//        Users userDB = usersDao.findByUsername(user.getUsername());
        
        user.setActiveSession(0);
        usersDao.update(user);
        return true;
    }
    
}
