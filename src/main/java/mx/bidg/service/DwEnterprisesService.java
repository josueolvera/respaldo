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

/**
 *
 * @author sistemask
 */
public interface DwEnterprisesService {
    
    public List<DwEnterprises> findByGroupArea(CGroups idGroup, CAreas idArea);
    
}
