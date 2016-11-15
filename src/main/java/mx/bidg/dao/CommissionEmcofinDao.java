/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.time.LocalDateTime;
import mx.bidg.model.Commission_Emcofin;



/**
 *
 * @author Kevin Salvador
 */
public interface CommissionEmcofinDao extends InterfaceDao<Commission_Emcofin>{
    Commission_Emcofin finfByidEmployee(int idEmployee,LocalDateTime applicationDate);
}
