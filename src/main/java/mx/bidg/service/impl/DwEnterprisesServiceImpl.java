/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mx.bidg.dao.*;
import mx.bidg.model.*;
import mx.bidg.pojos.HierarchicalLevel;
import mx.bidg.service.DwEnterprisesService;
import mx.bidg.utils.GroupArrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DwEnterprisesServiceImpl implements DwEnterprisesService {

    @Autowired
    DwEnterprisesDao dao;

    @Autowired
    DwEmployeesDao dwEmployeesDao;

    @Autowired
    UsersDao usersDao;

    @Autowired
    CAreasDao areasDao;

    @Autowired
    CRegionsDao regionsDao;

    @Autowired
    CBranchsDao branchsDao;

    @Autowired
    CZonaDao cZonaDao;

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
        return usersDao.findByIdFetchDwEmployee(idUser).getDwEmployee().getDwEnterprise();
    }

    @Override
    public List<HierarchicalLevel> findHierarchicalStructure() {
        GroupArrays<DwEnterprises> groupArrays = new GroupArrays<>();

        ArrayList<DwEnterprises> dwEnterprises = (ArrayList<DwEnterprises>) dao.findAll();
        List<HierarchicalLevel> hierarchicalGroups = new ArrayList<>();
        ArrayList<ArrayList<DwEnterprises>> dwGroups = groupArrays.groupInArray(dwEnterprises, new GroupArrays.Filter<DwEnterprises>() {
            @Override
            public String filter(DwEnterprises item) {
                return item.getIdGroup() + "";
            }
        });

        for (ArrayList<DwEnterprises> dwList : dwGroups) {
            DwEnterprises dw = dwList.get(0);
            HierarchicalLevel level = new HierarchicalLevel(dw.getIdGroup(), dw.getGroup().getGroupName());
            level.setAlias(dw.getGroup().getAcronyms());
            hierarchicalGroups.add(level);

            ArrayList<ArrayList<DwEnterprises>> dwDistributors = groupArrays.groupInArray(dwList, new GroupArrays.Filter<DwEnterprises>() {
                @Override
                public String filter(DwEnterprises item) {
                    return item.getIdDistributor() + "";
                }
            });

            for (ArrayList<DwEnterprises> dwDistList : dwDistributors ) {
                DwEnterprises dwD = dwDistList.get(0);
                HierarchicalLevel level1 = new HierarchicalLevel(dwD.getIdDistributor(), dwD.getDistributor().getDistributorName());
                level1.setAlias(dwD.getDistributor().getAcronyms());
                level.addSubLevel(level1);

                ArrayList<ArrayList<DwEnterprises>> dwRegions = groupArrays.groupInArray(dwDistList, new GroupArrays.Filter<DwEnterprises>() {
                    @Override
                    public String filter(DwEnterprises item) {
                        return item.getIdRegion() + "";
                    }
                });

                for (ArrayList<DwEnterprises> dwRegionsList : dwRegions) {
                    DwEnterprises dwR = dwRegionsList.get(0);
                    HierarchicalLevel level2 = new HierarchicalLevel(dwR.getIdRegion(), dwR.getRegion().getRegionName());
                    level2.setAlias(dwR.getRegion().getRegionName());
                    level1.addSubLevel(level2);

                    ArrayList<ArrayList<DwEnterprises>> dwZonas = groupArrays.groupInArray(dwRegionsList, new GroupArrays.Filter<DwEnterprises>() {
                            @Override
                            public String filter(DwEnterprises item) {
                                return item.getIdZona() + "";
                            }
                        });

                        for(ArrayList<DwEnterprises> dwZonasList : dwZonas){

                            DwEnterprises dwZ = dwZonasList.get(0);
                            HierarchicalLevel level3 = new HierarchicalLevel(dwZ.getIdZona(), dwZ.getZona().getName());
                            level3.setAlias(dwZ.getZona().getName());
                            level2.addSubLevel(level3);

                            ArrayList<ArrayList<DwEnterprises>> dwBranches = groupArrays.groupInArray(dwZonasList, new GroupArrays.Filter<DwEnterprises>() {
                                @Override
                                public String filter(DwEnterprises item) {
                                    return item.getIdBranch() + "";
                                }
                            });

                            for (ArrayList<DwEnterprises> dwBranchesList : dwBranches) {
                                DwEnterprises dwB = dwBranchesList.get(0);
                                HierarchicalLevel level4 = new HierarchicalLevel(dwB.getIdBranch(), dwB.getBranch().getBranchName());
                                level4.setAlias(dwB.getBranch().getBranchShort());
                                level3.addSubLevel(level4);

                                ArrayList<ArrayList<DwEnterprises>> dwAreas = groupArrays.groupInArray(dwBranchesList, new GroupArrays.Filter<DwEnterprises>() {
                                    @Override
                                    public String filter(DwEnterprises item) {
                                        return item.getIdArea() + "";
                                    }
                                });

                                    for(ArrayList<DwEnterprises> dwAreasList : dwAreas) {
                                        DwEnterprises dwA = dwAreasList.get(0);
                                        HierarchicalLevel level5 = new HierarchicalLevel(dwA.getIdArea(), dwA.getArea().getAreaName());
                                        level5.setAlias(dwA.getArea().getAreaName());
                                        level4.addSubLevel(level5);

                                        for (DwEnterprises item : dwAreasList) {
                                            item.setDwEmployeesList(dwEmployeesDao.findByDwEnterprisesId(item));
                                            level5.setDwEnterprise(item);
                                        }


                                    } // For Areas
                            } // For Branches
                        } // For Zonas
                } // For Region
            } // For Distribuidor
        } // For Grupos

        return hierarchicalGroups;
    }


    @Override
    public List<DwEnterprises> findByDistributor(Integer idDistributor) {
        return dao.findByDistributor(idDistributor);
    }

    @Override
    public List<DwEnterprises> findByDistributorAndArea(Integer idDistributor, Integer idArea) {
        return dao.findByDistributorAndArea(idDistributor, idArea);
    }

    @Override
    public DwEnterprises save(DwEnterprises dwEnterprises) {
        return dao.save(dwEnterprises);
    }

    @Override
    public DwEnterprises update(DwEnterprises dwEnterprises) {
        return dao.update(dwEnterprises);
    }

    @Override
    public DwEnterprises findByDistributorRegionBranch(Integer idDistributor, Integer idRegion, Integer idBranch) {
        return dao.findByDistributorRegionBranch(idDistributor,idRegion,idBranch);
    }

    @Override
    public DwEnterprises findByBranch(Integer idBranch) {
        return dao.findByBranch(idBranch);
    }

    @Override
    public List<DwEnterprises> findByBranches(Integer idBranch) {
        return dao.findByBranches(idBranch);
    }

    @Override
    public List<DwEnterprises> findAll() {
        return dao.findAll();
    }

    @Override
    public DwEnterprises findByBranchAndArea(Integer idBranch, Integer idArea) {
        return dao.findByBranchAndArea(idBranch,idArea);
    }

    @Override
    public List<CAreas> findAreaByDistributor(Integer idDistributor) {
        List<DwEnterprises> dwEnterprises = dao.findByDistributor(idDistributor);
        List<CAreas> areas = new ArrayList<CAreas>();
        for(DwEnterprises dwEnterprise : dwEnterprises){
            areas.add(areasDao.findById(dwEnterprise.getIdArea()));
        }
        return areas;
    }

    @Override
    public List<CRegions> findRegionByDistributor(Integer idDistributor) {
        List<DwEnterprises> dwEnterprises = dao.findByDistributor(idDistributor);
        List<CRegions> regions = new ArrayList<CRegions>();
        for (DwEnterprises dwEnterprise : dwEnterprises){
            regions.add(regionsDao.findById(dwEnterprise.getIdRegion()));
        }
        return regions;
    }

    @Override
    public List<CZonas> findZonaByDistributorAndRegion(Integer idDistributor, Integer idRegion) {
        List<DwEnterprises> dwEnterprises = dao.findZonaByDistributorAndRegion(idDistributor,idRegion);
        List<CZonas> zonas = new ArrayList<CZonas>();
        for(DwEnterprises dwEnterprise : dwEnterprises){
            zonas.add(cZonaDao.findById(dwEnterprise.getIdZona()));
        }
        return zonas;
    }

    @Override
    public List<CAreas> findAreaByBranch(Integer idBranch) {
        List<DwEnterprises> dwEnterprises = dao.findByBranches(idBranch);
        List<CAreas> areas = new ArrayList<CAreas>();
        for (DwEnterprises dwEnterprise : dwEnterprises){
            areas.add(areasDao.findById(dwEnterprise.getIdArea()));
        }
        return areas;
    }

    @Override
    public List<CBranchs> findBranchByDistributorAndRegionAndZona(Integer idDistributor, Integer idRegion, Integer idZona) {
        List<DwEnterprises> dwEnterprises = dao.findBranchByDistributorAndRegionAndZona(idDistributor, idRegion, idZona);
        List<CBranchs> branchs = new ArrayList<CBranchs>();
        for (DwEnterprises dwEnterprise : dwEnterprises){
            branchs.add(branchsDao.findById(dwEnterprise.getIdBranch()));
        }
        return branchs;
    }

    @Override
    public List<DwEnterprises> findByDistributorRegionZonaBranchAndArea(Integer idDistributor, Integer idRegion, Integer idZona, Integer idBranch, Integer idArea) {
        return dao.findByDistributorRegionZonaBranchAndArea(idDistributor, idRegion, idZona, idBranch, idArea);
    }
}
