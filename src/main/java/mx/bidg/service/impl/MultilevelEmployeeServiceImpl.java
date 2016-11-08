/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.MultilevelEmployeeDao;
import mx.bidg.model.MultilevelEmployee;
import mx.bidg.service.MultilevelEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MultilevelEmployeeServiceImpl implements MultilevelEmployeeService {
    
    @Autowired
    MultilevelEmployeeDao multilevelEmployeeDao;
    
    @Override
    public MultilevelEmployee save(MultilevelEmployee multilevelEmployee) {
        
        return multilevelEmployeeDao.save(multilevelEmployee);
    }

    @Override
    public MultilevelEmployee findById(Integer idMultilevel) {
        
        return multilevelEmployeeDao.findById(idMultilevel);
    }

    @Override
    public MultilevelEmployee update(MultilevelEmployee multilevelEmployee) {
        
        return multilevelEmployeeDao.update(multilevelEmployee);
    }

    @Override
    public List<MultilevelEmployee> findAll() {
        
        return multilevelEmployeeDao.findAll();
    }

    @Override
    public boolean delete(MultilevelEmployee multilevelEmployee) {
        
        return multilevelEmployeeDao.delete(multilevelEmployee);
    }

    @Override
    public List findByIdBranch(Integer idBranch) {
        return multilevelEmployeeDao.findByIdBranch(idBranch);
    }

    @Override
    public List<MultilevelEmployee> findAllActives() {
        return multilevelEmployeeDao.FindAllActives();
    }

    @Override
    public List<MultilevelEmployee> findByMultilevelEmployee(Integer idMultilevelEmployee) {
        return multilevelEmployeeDao.findByIdEmployeeMultilevel(idMultilevelEmployee);
    }

    @Override
    public MultilevelEmployee findByEmployee(Integer idEmployee) {
        return multilevelEmployeeDao.findByEmployee(idEmployee);
    }

}
