/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface UsersService {
    
    public Users findByUserName(String username);
    
    public Users verifyUserLogin(Users user);
    
    public boolean logout(Users user);
            
}
