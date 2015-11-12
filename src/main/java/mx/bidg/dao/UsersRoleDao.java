/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.UsersRole;

/**
 *
 * @author sistemask
 */
public interface UsersRoleDao extends InterfaceDao<UsersRole> {
    
    public List<UsersRole> findAllByUserId(int idUser);
    
}
