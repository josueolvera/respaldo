/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.time.LocalDateTime;

import mx.bidg.model.CommissionEmcofin;



/**
 *
 * @author Kevin Salvador
 */
public interface CommissionEmcofinDao extends InterfaceDao<CommissionEmcofin>{
    CommissionEmcofin finfByidEmployee(int idEmployee,LocalDateTime applicationDate);
}
