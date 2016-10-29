/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.MultilevelEmployee;

/**
 *
 * @author Cristhian de la cruz
 */
public interface MultilevelEmployeeService {
    
    MultilevelEmployee save(MultilevelEmployee multilevelEmployee);
    
    MultilevelEmployee findById(Integer idMultilevel);
    
    MultilevelEmployee update(MultilevelEmployee multilevelEmployee);
    
    List<MultilevelEmployee> findAll();
    
    boolean delete(MultilevelEmployee multilevelEmployee);

    List findByIdBranch(Integer idBranch);
    
    
    
}
