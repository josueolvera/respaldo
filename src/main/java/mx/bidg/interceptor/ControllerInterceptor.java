/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.bidg.config.Permissions;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author sistemask
 */

public class ControllerInterceptor extends HandlerInterceptorAdapter {
    
//    @Autowired
//    Permissions permissions;
//    
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
//        System.out.println(permissions);
//        return true;
//    }
    
}
