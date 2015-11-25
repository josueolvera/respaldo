/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CGroupsDao;
import mx.bidg.model.CGroups;
import mx.bidg.service.CGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CGroupsServiceImpl implements CGroupsService {
    
    @Autowired
    CGroupsDao dao;

    @Override
    public List<CGroups> findAll() {
        return dao.findAll();
    }
    
}
