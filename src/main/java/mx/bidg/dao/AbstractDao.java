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
import javax.servlet.http.HttpSession;
import mx.bidg.model.GlobalTracer;
import mx.bidg.model.Users;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sistemask
 */
public abstract class AbstractDao<PK extends Serializable, T> {
    
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HttpServletRequest request;

    @SuppressWarnings("unchecked")
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
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
        HttpSession session = request.getSession(false);
        if(session == null) {
            return;
        }
        Users user = (Users) session.getAttribute("user");

        if (user != null && user.getIdUser() != null && user.getUsername() != null) {
            System.out.println(user.getIdUser());
            System.out.println(user.getUsername());
            String task = request.getMethod().toLowerCase() + ":" + request.getRequestURI();
            GlobalTracer tracer = new GlobalTracer();
            ObjectMapper mapper = new ObjectMapper();
            Table annotation = entity.getClass().getAnnotation(Table.class);
            tracer.setIdUser(user.getIdUser());
            tracer.setUsername(user.getUsername());
            tracer.setOperationType(operationType);
            tracer.setTask(task);
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
    
}
