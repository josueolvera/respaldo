/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.AccessLevelFiltered;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.GlobalTracer;
import mx.bidg.model.Users;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;

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
        getSession().persist(entity);
        globalTracer("INSERT", entity);
    }
    
    public void remove(T entity){
        getSession().delete(entity);
        globalTracer("DELETE", entity);
    }
    
    public void modify(T entity){
        getSession().update(entity);
        globalTracer("UPDATE", entity);
    }
    
    protected Criteria createEntityCriteria() {
        Criteria entityCriteria = getSession().createCriteria(persistentClass);
        if (AccessLevelFiltered.class.isAssignableFrom(persistentClass)) {
            HttpSession session = request.getSession(false);
            if(session != null) {
                Users user = (Users) session.getAttribute("user");
                Disjunction disjunction = Restrictions.disjunction();

                for (Integer level : user.getAccessLevels()) {
                    disjunction.add(Restrictions.eq("idAccessLevel", level));
                }

                entityCriteria.add(disjunction);
            }
        }
        return entityCriteria;
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
            ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
            Table annotation = entity.getClass().getAnnotation(Table.class);
            tracer.setIdUser(user.getIdUser());
            tracer.setUsername(user.getUsername());
            tracer.setOperationType(operationType);
            tracer.setTask(task);
            tracer.setTableName(annotation.name());
            tracer.setIdEntity(entity.hashCode());
            tracer.setCreationDate(LocalDateTime.now());
            try {
                tracer.setInfo(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(entity));
            } catch (JsonProcessingException ex) {
                throw new ValidationException(ex.getMessage());
            }
            getSession().save(tracer);
        }
    }
    
}
