/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.Multilevel;
import mx.bidg.model.MultilevelEmployee;

import java.util.List;

/**
 *
 * @author Cristhian de la cruz
 */
public interface MultilevelEmployeeDao extends InterfaceDao<MultilevelEmployee>{

    List findByIdBranch(Integer idBranch);
    List<MultilevelEmployee> findByIdEmployeeMultilevel(Integer idEmployeeMultilevel);

}
