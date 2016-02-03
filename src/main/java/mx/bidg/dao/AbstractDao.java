/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import mx.bidg.model.GlobalTracer;
import mx.bidg.model.Users;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Table;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
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
    
    @Autowired
    private HttpSession session;
    
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public T getByKey(PK key){
        return (T) getSession().get(persistentClass, key);
    }
    
    public void persist(T entity){
        globalTracer("INSERT", entity);
        getSession().persist(entity);
    }
    
    public void remove(T entity){
        globalTracer("DELETE", entity);
        getSession().delete(entity);
    }
    
    public void modify(T entity){
        globalTracer("UPDATE", entity);
        getSession().update(entity);
    }
    
    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }
    
    public void globalTracer(String operationType, T entity) {
        GlobalTracer tracer = new GlobalTracer();
        Users user = (Users) session.getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        tracer.setIdUser(user);
        tracer.setUsername(user.getUsername());
        tracer.setOperationType(operationType);
        Table annotation = entity.getClass().getAnnotation(Table.class);
        tracer.setTableName(annotation.name());
        tracer.setCreationDate(LocalDateTime.now());
        try {
            tracer.setInfo(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(entity));
        } catch (JsonProcessingException ex) {
            throw new ValidationException(ex.getMessage());
        }
        getSession().save(tracer);
    }
    
}
