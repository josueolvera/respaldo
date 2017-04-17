/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.ArrayList;
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
    DwEnterprisesDao dwEnterprisesDao;

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

    @Autowired
    CDistributorsDao distributorsDao;

    @Override
    public List<DwEnterprises> findByGroupArea(CGroups idGroup, CAreas idArea) {
        return dwEnterprisesDao.findByGroupArea(idGroup, idArea);
    }

    @Override
    public DwEnterprises findByCombination(int idGroup, int idDistributor, int idRegion, int idBranch, int idArea) {
        CGroups group = new CGroups(idGroup);
        CDistributors distributor = new CDistributors(idDistributor);
        CRegions region = new CRegions(idRegion);
        CBranchs branch = new CBranchs(idBranch);
        CAreas area = new CAreas(idArea);
        return dwEnterprisesDao.findByCombination(group, distributor, region, branch, area);
    }

    @Override
    public DwEnterprises findById(int idDwEnterprise) {
        return dwEnterprisesDao.findById(idDwEnterprise);
    }

    @Override
    public DwEnterprises findByIdUser(int idUser) {
        return usersDao.findByIdFetchDwEmployee(idUser).getDwEmployee().getDwEnterprise();
    }

    @Override
    public List<HierarchicalLevel> findHierarchicalStructure() {
        GroupArrays<DwEnterprises> groupArrays = new GroupArrays<>();

        ArrayList<DwEnterprises> dwEnterprises = (ArrayList<DwEnterprises>) dwEnterprisesDao.findAll();
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
        return dwEnterprisesDao.findByDistributor(idDistributor);
    }

    @Override
    public List<DwEnterprises> findByDistributorAndArea(Integer idDistributor, Integer idArea) {
        return dwEnterprisesDao.findByDistributorAndArea(idDistributor, idArea);
    }

    @Override
    public DwEnterprises save(DwEnterprises dwEnterprises) {
        return dwEnterprisesDao.save(dwEnterprises);
    }

    @Override
    public DwEnterprises update(DwEnterprises dwEnterprises) {
        return dwEnterprisesDao.update(dwEnterprises);
    }

    @Override
    public DwEnterprises findByDistributorRegionBranch(Integer idDistributor, Integer idRegion, Integer idBranch) {
        return dwEnterprisesDao.findByDistributorRegionBranch(idDistributor,idRegion,idBranch);
    }

    @Override
    public DwEnterprises findByBranch(Integer idBranch) {
        return dwEnterprisesDao.findByBranch(idBranch);
    }

    @Override
    public List<DwEnterprises> findByBranches(Integer idBranch) {
        return dwEnterprisesDao.findByBranches(idBranch);
    }

    @Override
    public List<DwEnterprises> findAll() {
        return dwEnterprisesDao.findAll();
    }

    @Override
    public DwEnterprises findByBranchAndArea(Integer idBranch, Integer idArea) {
        return dwEnterprisesDao.findByBranchAndArea(idBranch,idArea);
    }

    @Override
    public List<CAreas> findAreaByDistributor(Integer idDistributor) {
        List<DwEnterprises> dwEnterprises = dwEnterprisesDao.findByDistributor(idDistributor);
        List<CAreas> areas = new ArrayList<CAreas>();
        for(DwEnterprises dwEnterprise : dwEnterprises){
            areas.add(areasDao.findById(dwEnterprise.getIdArea()));
        }
        return areas;
    }

    @Override
    public List<CBranchs> getBranchByDistributor(Integer idDistributor) {
        List<DwEnterprises> dwEnterprises = dwEnterprisesDao.findByDistributor(idDistributor);
        List<CBranchs> branchs = new ArrayList<>();
        for(DwEnterprises dwEnterprise : dwEnterprises){
            branchs.add(dwEnterprise.getBranch());
        }
        return branchs;
    }

    @Override
    public List<CRegions> findRegionByDistributor(Integer idDistributor) {
        List<DwEnterprises> dwEnterprises = dwEnterprisesDao.findByDistributor(idDistributor);
        List<CRegions> regions = new ArrayList<CRegions>();
        for (DwEnterprises dwEnterprise : dwEnterprises){
            regions.add(regionsDao.findById(dwEnterprise.getIdRegion()));
        }
        return regions;
    }

    @Override
    public List<CZonas> findZonaByDistributorAndRegion(Integer idDistributor, Integer idRegion) {
        List<DwEnterprises> dwEnterprises = dwEnterprisesDao.findZonaByDistributorAndRegion(idDistributor,idRegion);
        List<CZonas> zonas = new ArrayList<CZonas>();
        for(DwEnterprises dwEnterprise : dwEnterprises){
            zonas.add(cZonaDao.findById(dwEnterprise.getIdZona()));
        }
        return zonas;
    }

    @Override
    public List<CAreas> findAreaByBranch(Integer idBranch) {
        List<DwEnterprises> dwEnterprises = dwEnterprisesDao.findByBranches(idBranch);
        List<CAreas> areas = new ArrayList<CAreas>();
        for (DwEnterprises dwEnterprise : dwEnterprises){
            areas.add(areasDao.findById(dwEnterprise.getIdArea()));
        }
        return areas;
    }

    @Override
    public DwEnterprises findByDistributorBranchArea(Integer idDistributor, Integer idBranch, Integer idArea) {
        return dwEnterprisesDao.findByDistributorBranchArea(idDistributor, idBranch, idArea);
    }

    @Override
    public List<CRegions> findRegionByDistributorAndArea(Integer idDistributor, Integer idArea) {
        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributorAndArea(idDistributor, idArea);
        List<CRegions> regions = new ArrayList<CRegions>();
        for (DwEnterprises dwEnterprise : dwEnterprisesList){
            regions.add(regionsDao.findById(dwEnterprise.getIdRegion()));
        }
        return regions;
    }

    @Override
    public List<CBranchs> findBranchByDistributorAndArea(Integer idDistributor, Integer idArea) {
        List<DwEnterprises> dwEnterprises = dwEnterprisesDao.findByDistributorAndArea(idDistributor, idArea);
        List<CBranchs> branchs = new ArrayList<CBranchs>();
        for (DwEnterprises dwEnterprise : dwEnterprises){
            branchs.add(branchsDao.findById(dwEnterprise.getIdBranch()));
        }
        return branchs;
    }

    @Override
    public List<DwEnterprises> finDwEnterprisesByDistributorSaem(Integer idDistributor, Integer idReporte) {

        List<DwEnterprises> dwEnterprisesList;

        if(idDistributor.equals(0)){
            if (idReporte.equals(1)){
                List<CDistributors> distributorsList = distributorsDao.getDistributorsBySaemReports(null,true);
                dwEnterprisesList = dwEnterprisesDao.findByDistributorsSaem(distributorsList);
            }else {
                List<CDistributors> distributorsList = distributorsDao.getDistributorsBySaemReports(null, false);
                dwEnterprisesList = dwEnterprisesDao.findByDistributorsSaem(distributorsList);
            }
        } else {
            CDistributors distributor = distributorsDao.findById(idDistributor);
            List<CDistributors> distributorsList = distributorsDao.getDistributorsBySaemReports(idDistributor,distributor.getSaemFlag());
            dwEnterprisesList = dwEnterprisesDao.findByDistributorsSaem(distributorsList);
        }

        return dwEnterprisesList;
    }

    @Override
    public List<DwEnterprises> findDwEnterprisesByRegionSaem(Integer idRegion, Integer idReporte) {
        List<DwEnterprises> dwEnterprisesList;

        if(idRegion.equals(0)){
            if (idReporte.equals(1)){
                List<CRegions> regionsList = regionsDao.findRegionsBySaemFlag(null, 1);
                dwEnterprisesList = dwEnterprisesDao.findByRegionsSaem(regionsList);
            }else {
                List<CRegions> regionsList = regionsDao.findRegionsBySaemFlag(null, 0);
                dwEnterprisesList = dwEnterprisesDao.findByRegionsSaem(regionsList);
            }
        } else {
            CRegions region = regionsDao.findById(idRegion);
            List<CRegions> regionsList = regionsDao.findRegionsBySaemFlag(idRegion, region.getSaemFlag());
            dwEnterprisesList = dwEnterprisesDao.findByRegionsSaem(regionsList);
        }

        return dwEnterprisesList;
    }

    @Override
    public List<DwEnterprises> findDwEnterprisesByZonaSaem(Integer idZonas, Integer idReporte) {
        List<DwEnterprises> dwEnterprisesList;

        if(idZonas.equals(0)){
            if (idReporte.equals(1)){
                List<CZonas> zonasList = cZonaDao.findBySaemFlag(null, 1);
                dwEnterprisesList = dwEnterprisesDao.findByZonasSaem(zonasList);
            }else {
                List<CZonas> zonasList = cZonaDao.findBySaemFlag(null, 0);
                dwEnterprisesList = dwEnterprisesDao.findByZonasSaem(zonasList);
            }
        } else {
            CZonas zonas = cZonaDao.findById(idZonas);
            List<CZonas> zonasList = cZonaDao.findBySaemFlag(idZonas, zonas.getSaemFlag());
            dwEnterprisesList = dwEnterprisesDao.findByZonasSaem(zonasList);
        }

        return dwEnterprisesList;
    }

    @Override
    public List<DwEnterprises> findDwEnterprisesByBranchSaem(Integer idBranch, Integer idReporte) {
        List<DwEnterprises> dwEnterprisesList;

        if(idBranch.equals(0)){
            if (idReporte.equals(1)){
                List<CBranchs> branchsList = branchsDao.findBySaemFlag(null, 1);
                dwEnterprisesList = dwEnterprisesDao.findByBranchsSaem(branchsList);
            }else {
                List<CBranchs> branchsList = branchsDao.findBySaemFlag(null, 0);
                dwEnterprisesList = dwEnterprisesDao.findByBranchsSaem(branchsList);
            }
        } else {
            CBranchs branch = branchsDao.findById(idBranch);
            List<CBranchs> branchsList = branchsDao.findBySaemFlag(idBranch, branch.getSaemFlag());
            dwEnterprisesList = dwEnterprisesDao.findByBranchsSaem(branchsList);
        }

        return dwEnterprisesList;
    }

    @Override
    public List<DwEnterprises> findDwEnterprisesByAreaSaem(Integer idArea, Integer idReporte) {
        List<DwEnterprises> dwEnterprisesList;

        if(idArea.equals(0)){
            if (idReporte.equals(1)){
                List<CAreas> areasList = areasDao.findBySaemFlag(null, 1);
                dwEnterprisesList = dwEnterprisesDao.findByAreasSaem(areasList);
            }else {
                List<CAreas> areasList = areasDao.findBySaemFlag(null, 0);
                dwEnterprisesList = dwEnterprisesDao.findByAreasSaem(areasList);
            }
        } else {
            CAreas areas = areasDao.findById(idArea);
            List<CAreas> areasList = areasDao.findBySaemFlag(idArea, areas.getSaemFlag());
            dwEnterprisesList = dwEnterprisesDao.findByAreasSaem(areasList);
        }

        return dwEnterprisesList;
    }

    @Override
    public List<CBranchs> findBranchByDistributorAndRegionAndZona(Integer idDistributor, Integer idRegion, Integer idZona) {
        List<DwEnterprises> dwEnterprises = dwEnterprisesDao.findBranchByDistributorAndRegionAndZona(idDistributor, idRegion, idZona);
        List<CBranchs> branchs = new ArrayList<CBranchs>();
        for (DwEnterprises dwEnterprise : dwEnterprises){
            branchs.add(branchsDao.findById(dwEnterprise.getIdBranch()));
        }
        return branchs;
    }

    @Override
    public List<DwEnterprises> findByDistributorRegionZonaBranchAndArea(Integer idDistributor, Integer idRegion, Integer idZona, Integer idBranch, Integer idArea) {
        return dwEnterprisesDao.findByDistributorRegionZonaBranchAndArea(idDistributor, idRegion, idZona, idBranch, idArea);
    }

    @Override
    public List<Integer> getDistributorsByBussinessLine(Integer idBusinessLine) {
        return dwEnterprisesDao.getDistributorsByBussinessLine(idBusinessLine);
    }
}
