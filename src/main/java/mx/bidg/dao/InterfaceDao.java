/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sistemask
 */
public interface InterfaceDao<T extends Serializable> {
    
    public T save(T entity);
    
    public T findById(int id);
    
    public List<T> findAll();
    
    public T update(T entity);
    
    public boolean delete(T entity);
    
}
