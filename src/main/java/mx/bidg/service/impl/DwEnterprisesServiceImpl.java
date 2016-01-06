/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBranchs;
import mx.bidg.model.CDistributors;
import mx.bidg.model.CGroups;
import mx.bidg.model.CRegions;
import mx.bidg.model.DwEnterprises;
import mx.bidg.service.DwEnterprisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DwEnterprisesServiceImpl implements DwEnterprisesService {

    @Autowired
    DwEnterprisesDao dao;
    
    @Autowired
    UsersDao usersDao;
    
    @Override
    public List<DwEnterprises> findByGroupArea(CGroups idGroup, CAreas idArea) {
        return dao.findByGroupArea(idGroup, idArea);
    }

    @Override
    public DwEnterprises findByCombination(int idGroup, int idDistributor, int idRegion, int idBranch, int idArea) {
        CGroups group = new CGroups(idGroup);
        CDistributors distributor = new CDistributors(idDistributor);
        CRegions region = new CRegions(idRegion);
        CBranchs branch = new CBranchs(idBranch);
        CAreas area = new CAreas(idArea);
        return dao.findByCombination(group, distributor, region, branch, area);
    }

    @Override
    public DwEnterprises findById(int idDwEnterprise) {
        return dao.findById(idDwEnterprise);
    }

    @Override
    public DwEnterprises findByIdUser(int idUser) {
        return usersDao.findByIdFetchDwEmployee(idUser).getIdDwEmployee().getIdDwEnterprise();
    }
    
}
