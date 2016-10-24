/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.MultilevelEmployeeDao;
import mx.bidg.model.MultilevelEmployee;
import org.springframework.stereotype.Repository;

@Repository
public class MultilevelEmployeeDaoImpl extends AbstractDao <Integer, MultilevelEmployee> implements MultilevelEmployeeDao {

    @Override
    public MultilevelEmployee save(MultilevelEmployee entity) {
        persist(entity);
        return entity;
    }

    @Override
    public MultilevelEmployee findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<MultilevelEmployee> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public MultilevelEmployee update(MultilevelEmployee entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(MultilevelEmployee entity) {
        remove(entity);
        return true;
    }
    
}
