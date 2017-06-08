/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.Requests;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface RequestsDao extends InterfaceDao<Requests> {

    Requests findByIdFetchBudgetMonthBranch(Integer idRequest);
    Requests findByIdFetchStatus(Integer idRequest);
    Requests findByIdFetchCategory(Integer idRequest);
    Requests findByFolio(String folio);
    List<Requests> findByRequestCategory(Integer idRequestCategory);
    List<Requests> findByCategoryAndTypeByEmployee(Integer idRequestCategory, Integer idRequestType, Integer idEmployee);
    List<Requests> findByCategoryAndTypeAndStatus(Integer idRequestCategory, Integer idRequestType);
    List<Requests> findByCategoryAndType(Integer idRequestCategory, Integer idRequestType);
}
