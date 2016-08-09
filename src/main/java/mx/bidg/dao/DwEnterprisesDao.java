/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.CAreas;
import mx.bidg.model.CBranchs;
import mx.bidg.model.CDistributors;
import mx.bidg.model.CGroups;
import mx.bidg.model.CRegions;
import mx.bidg.model.DwEnterprises;

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
    DwEnterprises findByBranch (Integer idBranch);
    List<DwEnterprises> findByBranches (Integer idBranch);
    DwEnterprises findByBranchAndArea(Integer idBranch, Integer idArea);
}
