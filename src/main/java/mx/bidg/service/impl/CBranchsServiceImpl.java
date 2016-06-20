/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CBranchsDao;
import mx.bidg.model.CBranchs;
import mx.bidg.service.CBranchsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CBranchsServiceImpl implements CBranchsService {
    
    @Autowired
    CBranchsDao cBranchsDao;

    @Override
    public List<CBranchs> findAll() {
        return cBranchsDao.findAll();
    }
    
}
