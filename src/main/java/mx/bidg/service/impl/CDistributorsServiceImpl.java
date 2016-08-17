/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CDistributorsDao;
import mx.bidg.model.CDistributors;
import mx.bidg.service.CDistributorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CDistributorsServiceImpl implements CDistributorsService {
    
    @Autowired
    private CDistributorsDao cDistributorsDao;

    @Override
    public List<CDistributors> findAll() {
        return cDistributorsDao.findAll();
    }

    @Override
    public List<CDistributors> findAllForStock() {
        return cDistributorsDao.findAllForStock();
    }

    @Override
    public List<CDistributors> findAllForAgreement() {
        return cDistributorsDao.findAllForAgreement();
    }

    @Override
    public List<CDistributors> getDistributors(Boolean forStock, Boolean forBudget, Boolean forAgreement) {
        return cDistributorsDao.getDistributors(forStock, forBudget, forAgreement);
    }
}
