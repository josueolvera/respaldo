/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.BudgetMonthConcepts;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sistemask
 */
public interface InterfaceDao<T extends Serializable> {
    T save(T entity);
    T findById(int id);
    List<T> findAll();
    T update(T entity);
    boolean delete(T entity);
}
