/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.Users;
import mx.bidg.model.UsersRole;

/**
 *
 * @author sistemask
 */
public interface UsersRoleService {
    
    public List<UsersRole> findAllByUserId(Users idUser);
    
}
