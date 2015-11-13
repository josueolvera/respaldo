/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.interceptor;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import mx.bidg.model.Users;
import mx.bidg.service.UsersService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

/**
 *
 * @author sistemask
 */
@Component
public class MySessionListener implements HttpSessionListener {
    
    @Autowired
    UsersService usersService;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Sesion creada");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Users user = (Users) session.getAttribute("user");
        
        if(user != null) {
            
            usersService.logout(user);
            
        }
        System.out.println("Sesion destruida");
        
    }
    
}
