/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface UsersDao extends InterfaceDao<Users> {

    Users findByUsername(String username);
    Users findByIdFetchDwEmployee(int id);
    Users findByEmail(String email);
    Users findByDwEmpployee(Integer idDwEmployee);

}