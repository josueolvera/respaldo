/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.PeriodicsPayments;

/**
 *
 * @author sistemask
 */
public interface PeriodicPaymentsDao extends InterfaceDao<PeriodicsPayments> {
    
    public List<PeriodicsPayments> findByFolio(String folio);
    
}
