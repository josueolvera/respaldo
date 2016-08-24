/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;

import mx.bidg.model.*;
import org.apache.xmlbeans.impl.piccolo.xml.UnicodeLittleXMLDecoder;

/**
 *
 * @author sistemask
 */
public interface DwEnterprisesDao extends InterfaceDao<DwEnterprises> {
    List<DwEnterprises> findByGroupArea(CGroups idGroup, CAreas idArea);
    DwEnterprises findByCombination(CGroups group, CDistributors distributor, CRegions region, CBranchs branch, CAreas area);
    List<DwEnterprises> findByDistributor(Integer idDistributor);
    List<DwEnterprises> findByDistributorAndArea(Integer idDistributor, Integer idArea);
    DwEnterprises findByDistributorRegionBranch(Integer idDistributor, Integer idRegion, Integer idBranch);
    List<DwEnterprises> findByDistributorAndRegionAndBranchAndArea(Integer idDistributor, Integer idRegion, Integer idBranch,Integer idArea);
    List<DwEnterprises> findByDistributorRegionZonaBranchAndArea(Integer idDistributor, Integer idRegion,Integer idZona, Integer idBranch,Integer idArea);
    DwEnterprises findByBranch (Integer idBranch);
    List<DwEnterprises> findByBranches (Integer idBranch);
    DwEnterprises findByBranchAndArea(Integer idBranch, Integer idArea);
    List<DwEnterprises> findZonaByDistributorAndRegion(Integer idDistributor, Integer idRegion);
    List<DwEnterprises> findBranchByDistributorAndRegionAndZona(Integer idDistributor, Integer idRegion, Integer idZona);
    DwEnterprises findByDistributorBranchArea(Integer idDistributor, Integer idBranch, Integer idArea);
    List<DwEnterprises> findByDistributorsSaem(List <CDistributors> distributorsList);
    List<DwEnterprises> findByRegionsSaem(List<CRegions> regionsList);
    List<DwEnterprises> findByZonasSaem(List<CZonas> zonasList);
    List<DwEnterprises> findByBranchsSaem(List<CBranchs> branchsList);
    List<DwEnterprises> findByAreasSaem(List<CAreas> areasList);
    List<DwEnterprises> findByRegion (Integer idRegion);
    List<DwEnterprises> findByZona (Integer idZonas);
}
