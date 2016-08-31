/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.CAgreementsGroups;

import java.util.List;

/**
 *
 * @author josueolvera
 */
public interface CAgreementsGroupsDao extends InterfaceDao<CAgreementsGroups>{
    List<CAgreementsGroups> findGruoupActives ();
    
}
