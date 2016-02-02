/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sistemask
 */
public abstract class AbstractDao<PK extends Serializable, T> {
    
    private final Class<T> persistentClass;
    
    public AbstractDao(){
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    
    @Autowired
    private SessionFactory sessionFactory;
    
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public T getByKey(PK key){
        return (T) getSession().get(persistentClass, key);
    }
    
    public void persist(T entity){
        getSession().persist(entity);
    }
    
    public void remove(T entity){
        getSession().delete(entity);
    }
    
    public void modify(T entity){
        getSession().update(entity);
    }
    
    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }
    
}
