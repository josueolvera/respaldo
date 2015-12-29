/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CMonths;

/**
 *
 * @author sistemask
 */
public interface CMonthsService {
    
    public List<CMonths> findAll();
    public CMonths findById(int id);
    
}
