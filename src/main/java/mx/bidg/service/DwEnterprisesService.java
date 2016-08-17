/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;

import mx.bidg.model.*;
import mx.bidg.pojos.HierarchicalLevel;

import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author sistemask
 */
public interface DwEnterprisesService {
    List<DwEnterprises> findByGroupArea(CGroups idGroup, CAreas idArea);
    DwEnterprises findByCombination(int idGroup, int idDistributor, int idRegion, int idBranch, int idArea);
    DwEnterprises findById(int idDwEnterprise);
    DwEnterprises findByIdUser(int idUser);
    List<HierarchicalLevel> findHierarchicalStructure();
    List<DwEnterprises> findByDistributor(Integer idDistributor);
    List<DwEnterprises> findByDistributorAndArea(Integer idDistributor, Integer idArea);
    DwEnterprises save(DwEnterprises dwEnterprises);
    DwEnterprises update(DwEnterprises dwEnterprises);
    DwEnterprises findByDistributorRegionBranch (Integer idDistributor, Integer idRegion, Integer idBranch);
    DwEnterprises findByBranch(Integer idBranch);
    List<DwEnterprises> findByBranches(Integer idBranch);
    List<DwEnterprises> findAll();
    DwEnterprises findByBranchAndArea(Integer idBranch, Integer idArea);
    List<CAreas> findAreaByDistributor(Integer idDistributor);
    List<CBranchs> getBranchByDistributor(Integer idDistributor);
    List<CRegions> findRegionByDistributor(Integer idDistributor);
    List<CBranchs> findBranchByDistributorAndRegionAndZona(Integer idDistributor, Integer idRegion, Integer idZona);
    List<DwEnterprises> findByDistributorRegionZonaBranchAndArea(Integer idDistributor, Integer idRegion, Integer idZona, Integer idBranch, Integer idArea);
    List<CZonas> findZonaByDistributorAndRegion (Integer idDistributor, Integer idRegion);
    List<CAreas> findAreaByBranch(Integer idBranch);
}
