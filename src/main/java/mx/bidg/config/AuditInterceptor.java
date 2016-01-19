/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.config;

import java.io.Serializable;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author sistemask
 */
@Component
public class AuditInterceptor extends EmptyInterceptor {
    
    private static final long serialVersionUID = 1L;
    private String operation = "INSERT";
    boolean isMainEntity = false;
    
    @Autowired
    private HttpSession session;

    @Override
    public void preFlush(Iterator entities) {
        super.preFlush(entities); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postFlush(Iterator entities) {
        super.postFlush(entities); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return super.onSave(entity, id, state, propertyNames, types); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
//        Object ent = getEntity(getEntityName(entity), id);
//        System.out.println("ENT: " + ent);
//        System.out.println("ENTITY: " + entity);
        return super.onLoad(entity, id, state, propertyNames, types); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
//        System.out.println("Entity: " + entity);
//        System.out.println("Id: " + id);
//        System.out.println("CurrentState: " + currentState);
//        System.out.println("PreviousState: " + previousState);
//        for(String property : propertyNames) {
//            System.out.println("Property: " + property);
//        }
//        System.out.println("Types: " + types);
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        super.onDelete(entity, id, state, propertyNames, types); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
