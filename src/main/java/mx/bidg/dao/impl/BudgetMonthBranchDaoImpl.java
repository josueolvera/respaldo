/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.model.BudgetMonthBranch;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sistemask
 */
@Repository
public class BudgetMonthBranchDaoImpl extends AbstractDao<Integer, BudgetMonthBranch> implements BudgetMonthBranchDao{

    @Override
    public BudgetMonthBranch save(BudgetMonthBranch entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BudgetMonthBranch findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<BudgetMonthBranch> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BudgetMonthBranch update(BudgetMonthBranch entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(BudgetMonthBranch entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
