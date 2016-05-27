package mx.bidg.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.AccessLevelFilterable;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.GlobalTracer;
import mx.bidg.model.Users;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void persist(T entity) {
        getSession().persist(entity);
        globalTracer("INSERT", entity);
    }
    
    public void remove(T entity) {
        if (AccessLevelFilterable.class.isAssignableFrom(persistentClass)) {
            if (isAuthorized(entity)) {
                globalTracer("DELETE", entity);
                getSession().delete(entity);
            } else {
                throw new ValidationException(
                        "Operación DELETE sobre Registro: " + entity.hashCode() + entity.getClass() + " no autorizada",
                        "Operación no autorizada",
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else {
            globalTracer("DELETE", entity);
            getSession().delete(entity);
        }
    }
    
    public void modify(T entity) {
        if (AccessLevelFilterable.class.isAssignableFrom(persistentClass)) {
            if (isAuthorized(entity)) {
                getSession().update(entity);
                globalTracer("UPDATE", entity);
            } else {
                throw new ValidationException(
                        "Operación UPDATE sobre Registro: " + entity.hashCode() + entity.getClass() + " no autorizada",
                        "Operación no autorizada",
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else {
            getSession().update(entity);
            globalTracer("UPDATE", entity);
        }
    }
    
    protected Criteria createEntityCriteria() {
        Criteria entityCriteria = getSession().createCriteria(persistentClass);
        if (AccessLevelFilterable.class.isAssignableFrom(persistentClass)) {
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

    protected Criteria createEntityCriteriaNoAccessLevel() {
        return getSession().createCriteria(persistentClass);
    }

    private boolean isAuthorized(T entity) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return false;
        }

        Users user = (Users) session.getAttribute("user");
        if (user != null && user.getIdUser() != null && user.getUsername() != null) {
            AccessLevelFilterable enty = (AccessLevelFilterable) entity;
            return user.getAccessLevels().contains(enty.getIdAccessLevel());
        }

        return false;
    }
    
    public void globalTracer(String operationType, T entity) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return;
        }
        Users user = (Users) session.getAttribute("user");

        if (user != null && user.getIdUser() != null && user.getUsername() != null) {
            String task = request.getMethod().toLowerCase() + ":" + request.getRequestURI();
            GlobalTracer tracer = new GlobalTracer();
            ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
            if (entity instanceof HibernateProxy) {
                entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
            }
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
                Logger.getLogger("AbstractDao.globalTracer").log(Level.WARNING, ex.getMessage(), ex);
                throw new ValidationException(ex.getMessage());
            }
            getSession().save(tracer);
        }
    }
    
}
