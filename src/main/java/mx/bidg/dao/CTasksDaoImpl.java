/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.CTasks;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Repository
public class CTasksDaoImpl extends AbstractDao<Integer, CTasks> implements InterfaceDao<CTasks>, CTasksDao{

    @Override
    public CTasks save(CTasks entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CTasks findById(int id) {
        return getByKey(id);
    }

    //@Transactional
    @Override
    public List<CTasks> findAll() {
//        Criteria criteria = createEntityCriteria().
//                setFetchMode("tasksRoleList", FetchMode.JOIN)
//                .setFetchMode("tasksRoleList.idSystemRole", FetchMode.SELECT);
        
        Criteria criteria = createEntityCriteria().setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        
        return (List<CTasks>) criteria.list();
    }

    @Override
    public CTasks update(CTasks entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CTasks entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
