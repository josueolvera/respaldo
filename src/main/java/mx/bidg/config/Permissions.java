/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.bidg.model.CTasks;
import mx.bidg.model.TasksRole;
import mx.bidg.service.CTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sistemask
 */
@Component
public class Permissions {
    @Autowired
    CTasksService cTasksService;
    private HashMap<String,ArrayList<Integer>> map = null;

    public void buildTasksPermissions(){
        this.map = new HashMap<>();
        
        List<CTasks> tasks = cTasksService.findAll();
        for(CTasks task : tasks) {
           ArrayList<Integer> roles = new ArrayList<>();
           
           for(TasksRole taskRole : task.getTasksRoleList()) {
               roles.add(taskRole.getIdSystemRole().getIdSystemRole());
           }
           
           map.put(task.getHttpMethod().toLowerCase() + ":" + task.getTaskName().toLowerCase(), roles);
        }
        
    }

    public HashMap<String, ArrayList<Integer>> getMap() {
        if(map == null){
            buildTasksPermissions();
        }
        return map;
    }
    
}
