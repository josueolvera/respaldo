/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.utils;

/**
 *
 * @author sistemask
 */
public class Login {
    
    public static String getUser (String user){           
      return user.contains("@") ? (user.substring(0, user.indexOf('@'))): user;     
  }
    
}
