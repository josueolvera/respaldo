/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.bidg.config.Permissions;
import mx.bidg.model.Users;
import mx.bidg.model.UsersRole;
import mx.bidg.service.UsersRoleService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author sistemask
 */
@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    Permissions permissions;
    @Autowired
    UsersRoleService usersRoleService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        
        HttpSession session = request.getSession(false);
        
        if(session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Es necesario inciar sesion");
            return false;
        }
        
        Users user = (Users) session.getAttribute("user");
        
        if(user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Es necesario inciar sesion");
            return false;
        }
        
        HashMap<String, ArrayList<Integer>> map = permissions.getMap();
        
        List<UsersRole> userRoles = usersRoleService.findAllByUserId(user.getIdUser());
        System.out.println(request.getRequestURI());
        
        return true;
    }
    
}
