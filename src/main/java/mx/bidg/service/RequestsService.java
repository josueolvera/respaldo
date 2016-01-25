/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.Requests;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface RequestsService {
    
    Requests save(String data, Users user) throws Exception;
    
    Requests authorization(Integer idRequest);
    
}
