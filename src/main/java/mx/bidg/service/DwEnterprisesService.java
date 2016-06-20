/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CAreas;
import mx.bidg.model.CGroups;
import mx.bidg.model.DwEnterprises;
import mx.bidg.pojos.HierarchicalLevel;

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
    List<HierarchicalLevel>findHierarchicalStrucutureByAgreement();
}
