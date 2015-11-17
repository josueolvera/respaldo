/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.UsersDao;
import mx.bidg.exceptions.ActiveSessionException;
import mx.bidg.exceptions.InactiveUserException;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.ActiveSession;
import mx.bidg.model.Users;
import mx.bidg.service.ActiveSessionService;
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
    @Autowired
    ActiveSessionService activeSessionService;

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
        
        if(userDB.getStatus() == 0) {
            throw new InactiveUserException("Usuario con status inactivo. Username: " + user.getUsername());
        }
        
        ActiveSession activeSession = activeSessionService.findById(userDB.getIdUser());
        if(activeSession != null) {
            throw new ActiveSessionException("El usuario " + user.getUsername() + " ya tiene una sesion activa");
        }
        
        if(userDB.getPassword().equals(user.getPassword())) {
            return userDB;
        } else {
            throw new ValidationException("Contraseña incorrecta. Username: " + user.getUsername());
        }
    }
    
}
