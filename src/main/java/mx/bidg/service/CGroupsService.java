/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CGroups;

/**
 *
 * @author sistemask
 */
public interface CGroupsService {
    
    public List<CGroups> findAll();
    public CGroups getByIdBudgetsCatalogs(Integer idGroup);
    public CGroups getBudgetListByGroupsArea(Integer idGroup, Integer idArea);
    
}
