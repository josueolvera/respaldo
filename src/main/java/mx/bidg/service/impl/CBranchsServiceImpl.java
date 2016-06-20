/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.dao.CBranchsDao;
import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.model.*;
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

    @Autowired
    DwEnterprisesDao dwEnterprisesDao;

    @Override
    public List<CBranchs> findAll() {
        return cBranchsDao.findAll();
    }

    @Override
    public CBranchs findById(int idBranch) {
        return cBranchsDao.findById(idBranch);
    }

    @Override
    public boolean delete(int idBranch) {
        return cBranchsDao.delete(cBranchsDao.findById(idBranch));
    }

    @Override
    public CBranchs update(CBranchs cBranchs) {
        return cBranchsDao.update(cBranchs);
    }

    @Override
    public CBranchs save(CBranchs cBranchs, int idDistributor ,int idRegion) {
        cBranchs.setUploadedDate(LocalDateTime.now());
        cBranchs.setBranchName(cBranchs.getBranchName().toUpperCase());
        cBranchs.setBranchShort(cBranchs.getBranchShort().toUpperCase());
        cBranchs.setStatus(true);
        cBranchsDao.save(cBranchs);
        DwEnterprises dwEnterprises = new DwEnterprises();
        dwEnterprises.setBranch(cBranchs);
        dwEnterprises.setDistributor(new CDistributors(idDistributor));
        dwEnterprises.setRegion(new CRegions(idRegion));
        dwEnterprises.setGroup(new CGroups(1));
        dwEnterprises.setArea(new CAreas(2));
        dwEnterprises.setBudgetable(1);
        dwEnterprises.setStatus(true);
        dwEnterprisesDao.save(dwEnterprises);

        return cBranchs;
    }

    @Override
    public CBranchs changeBranchStatus(int idBranch) {
        CBranchs branch = cBranchsDao.findById(idBranch);
        branch.setStatus(false);
        cBranchsDao.update(branch);
        return branch;
    }

}
