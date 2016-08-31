/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CAgreementsGroups;

/**
 *
 * @author josueolvera
 */
public interface CAgreementsGroupsService {
    
    CAgreementsGroups save (CAgreementsGroups agreementsGroups);
    CAgreementsGroups update(CAgreementsGroups agreementsGroups);
    boolean delete(CAgreementsGroups agreementsGroups);
    CAgreementsGroups findById (Integer idAG);
    List<CAgreementsGroups> findAll();
    CAgreementsGroups lowGroup (Integer idAG);
    List<CAgreementsGroups> findGroupsActives();
    
}
