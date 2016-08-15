/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CBranchs;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface CBranchsService {
    
    List<CBranchs> findAll();
    CBranchs findById(int idBranch);
    boolean delete(int idBranch);
    CBranchs update(CBranchs data);
    CBranchs save(CBranchs cBranchs, int idDistributor ,int idRegion, int idZona);
    CBranchs changeBranchStatus(int idBranch, Users user);
}
