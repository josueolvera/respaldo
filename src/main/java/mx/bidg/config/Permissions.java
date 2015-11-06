/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.CTasksDao;
import mx.bidg.model.CTasks;
import mx.bidg.model.SystemRoles;
import mx.bidg.model.TasksRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */

public class Permissions {
    
    @Autowired
    private CTasksDao cTasksDao;
    private HashMap<String,ArrayList<Integer>> map;

    public Permissions() {
        buildTasksPermissions();
    }
    @Transactional
    public void buildTasksPermissions(){
        
        List<CTasks> tasks = cTasksDao.findAll();
        for(CTasks task : tasks) {
           ArrayList<Integer> roles = new ArrayList<>();
           
           for(TasksRole taskRole : task.getTasksRoleList()) {
               roles.add(taskRole.getIdSystemRole().getIdSystemRole());
           }
           
           map.put(task.getTaskName(), roles);
        }
        
    }

    public HashMap<String, ArrayList<Integer>> getMap() {
        return map;
    }
    
}
