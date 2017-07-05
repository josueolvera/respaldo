/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.io.OutputStream;
import java.util.List;
import mx.bidg.model.CDistributors;

/**
 *
 * @author sistemask
 */
public interface CDistributorsService {
    
    List<CDistributors> findAll();
    List<CDistributors> findAllForStock();
    List<CDistributors> findAllForAgreement();
    List<CDistributors> getDistributors(Boolean forStock, Boolean forBudget, Boolean forAgreement);
    List<CDistributors> getDistributorForSaem (Integer idDistributor,Boolean saemFlag);
    CDistributors findById(Integer idDistributor);
    CDistributors save(CDistributors cDistributors);
    CDistributors update(CDistributors cDistributors);
    boolean delete(CDistributors cDistributors);
    void exportFile(OutputStream stream) throws Exception;
}
