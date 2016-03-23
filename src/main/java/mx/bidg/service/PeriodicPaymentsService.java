/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.PeriodicsPayments;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface PeriodicPaymentsService {
    
    PeriodicsPayments saveData(String data) throws Exception;
    PeriodicsPayments findByFolio(String folio);
    PeriodicsPayments update(int idPayment, String data) throws Exception;
    
}
