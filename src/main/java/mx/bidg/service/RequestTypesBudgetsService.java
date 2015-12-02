/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.RequestTypesBudgets;

/**
 *
 * @author sistemask
 */
public interface RequestTypesBudgetsService {
    
    public List<RequestTypesBudgets> findByRequestType(CRequestTypes idRequestType);
    
}
