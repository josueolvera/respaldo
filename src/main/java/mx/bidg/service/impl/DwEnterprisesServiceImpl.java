/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.dao.UsersDao;
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

            for (ArrayList<DwEnterprises> dwDistList : dwDistributors) {
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

                    ArrayList<ArrayList<DwEnterprises>> dwBranches = groupArrays.groupInArray(dwRegionsList, new GroupArrays.Filter<DwEnterprises>() {
                        @Override
                        public String filter(DwEnterprises item) {
                            return item.getIdBranch() + "";
                        }
                    });

                    for (ArrayList<DwEnterprises> dwBranchesList : dwBranches) {
                        DwEnterprises dwB = dwBranchesList.get(0);
                        HierarchicalLevel level3 = new HierarchicalLevel(dwB.getIdBranch(), dwB.getBranch().getBranchName());
                        level3.setAlias(dwB.getBranch().getBranchShort());
                        level2.addSubLevel(level3);

                        ArrayList<ArrayList<DwEnterprises>> dwAreas = groupArrays.groupInArray(dwBranchesList, new GroupArrays.Filter<DwEnterprises>() {
                            @Override
                            public String filter(DwEnterprises item) {
                                return item.getIdArea() + "";
                            }
                        });

                        for (ArrayList<DwEnterprises> dwAreasList : dwAreas) {
                            DwEnterprises dwA = dwAreasList.get(0);
                            HierarchicalLevel level4 = new HierarchicalLevel(dwA.getIdArea(), dwA.getArea().getAreaName());
                            level4.setAlias(dwA.getArea().getAreaName());
                            level3.addSubLevel(level4);

                            for (DwEnterprises item : dwAreasList) {
                                item.setDwEmployeesList(dwEmployeesDao.findByDwEnterprisesId(item));
                                level4.setDwEnterprise(item);
                            }
                        }
                    }
                }
            }
        }
        return hierarchicalGroups;
    }

    @Override
    public List<DwEnterprises> findByDistributor(Integer idDistributor) {
        return dao.findByDistributor(idDistributor);
    }

    @Override
    public List<HierarchicalLevel> findHierarchicalStrucutureByAgreement() {
        GroupArrays<DwEnterprises> groupArrays = new GroupArrays<>();

        ArrayList<DwEnterprises> dwEnterprises = (ArrayList<DwEnterprises>) dao.findAllByStatusAgreement();
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

            for (ArrayList<DwEnterprises> dwDistList : dwDistributors) {
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

                    ArrayList<ArrayList<DwEnterprises>> dwBranches = groupArrays.groupInArray(dwRegionsList, new GroupArrays.Filter<DwEnterprises>() {
                        @Override
                        public String filter(DwEnterprises item) {
                            return item.getIdBranch() + "";
                        }
                    });

                    for (ArrayList<DwEnterprises> dwBranchesList : dwBranches) {
                        DwEnterprises dwB = dwBranchesList.get(0);
                        HierarchicalLevel level3 = new HierarchicalLevel(dwB.getIdBranch(), dwB.getBranch().getBranchName());
                        level3.setAlias(dwB.getBranch().getBranchShort());
                        level2.addSubLevel(level3);

                        ArrayList<ArrayList<DwEnterprises>> dwAreas = groupArrays.groupInArray(dwBranchesList, new GroupArrays.Filter<DwEnterprises>() {
                            @Override
                            public String filter(DwEnterprises item) {
                                return item.getIdArea() + "";
                            }
                        });

                        for (ArrayList<DwEnterprises> dwAreasList : dwAreas) {
                            DwEnterprises dwA = dwAreasList.get(0);
                            HierarchicalLevel level4 = new HierarchicalLevel(dwA.getIdArea(), dwA.getArea().getAreaName());
                            level4.setAlias(dwA.getArea().getAreaName());
                            level3.addSubLevel(level4);

                            for (DwEnterprises item : dwAreasList) {
                                item.setDwEmployeesList(dwEmployeesDao.findByDwEnterprisesId(item));
                                level4.setDwEnterprise(item);
                            }
                        }
                    }
                }
            }
        }
        return hierarchicalGroups;
    }
}
