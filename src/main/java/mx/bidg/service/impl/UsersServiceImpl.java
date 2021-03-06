/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.bidg.dao.UsersDao;
import mx.bidg.exceptions.InactiveUserException;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.ActiveSessionService;
import mx.bidg.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return usersDao.findByUsername(username);
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
            throw new ValidationException(
                    "El usuario " + user.getUsername() + " ya tiene una sesion activa",
                    "El usuario ya tiene una sesion activa",
                    HttpStatus.PRECONDITION_FAILED
            );
        }
        
        if(userDB.getPassword().equals(user.getPassword())) {

            Set<Integer> accessLevels = new HashSet<>();
            for (UsersRole userRole : userDB.getUsersRoleList()) {
                SystemRoles role = userRole.getIdSystemRole();
                for (AccessLevelsRole accessLevel : role.getAccessLevelsRoleList()) {
                    accessLevels.add(accessLevel.getAccessLevel().getIdAccessLevel());
                }
            }
            userDB.setAccessLevels(accessLevels);

            return userDB;
        } else {
            throw new ValidationException("Contraseña incorrecta. Username: " + user.getUsername());
        }
    }

    @Override
    public List<Users> findAll() {
        return usersDao.findAll();
    }


    @Override
    public Users findById(Integer idUser) {
        return usersDao.findByIdFetchDwEmployee(idUser);
    }
}
