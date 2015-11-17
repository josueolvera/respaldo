/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.ActiveSession;

/**
 *
 * @author sistemask
 */
public interface ActiveSessionService {
    
    public ActiveSession save(ActiveSession activeSession);
    public ActiveSession findById(int id);
    public boolean delete(ActiveSession activeSession);
    
}
