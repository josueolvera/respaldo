/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.config;

import java.util.List;
import mx.bidg.dao.CTasksDao;
import mx.bidg.dao.CTasksDaoImpl;
import mx.bidg.model.CTasks;

/**
 *
 * @author sistemask
 */
public class Prueba {
    
    public static void main(String[] args) {
//        Permissions permissions = new Permissions();
//        
//        System.out.println(permissions.getMap());
        CTasksDao cTasksDao = new CTasksDaoImpl();
        List<CTasks> tasks = cTasksDao.findAll();
        System.out.println(tasks);
    }
    
}
