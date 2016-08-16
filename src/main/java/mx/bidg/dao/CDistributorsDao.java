/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.CDistributors;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface CDistributorsDao extends InterfaceDao<CDistributors> {
    List<CDistributors> findAllForStock();
    List<CDistributors> findAllForAgreement();
    List<CDistributors> getDistributors(Boolean forStock, Boolean forBudget, Boolean forAgreement);
}
