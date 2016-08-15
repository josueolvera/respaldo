/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import mx.bidg.model.GroupsAgreements;

/**
 *
 * @author josueolvera
 */
public interface GroupsAgreementsService {
    GroupsAgreements save(GroupsAgreements groupsAgreements);
    GroupsAgreements update(GroupsAgreements groupsAgreements);
    GroupsAgreements findById(Integer idGa);
    List<GroupsAgreements> findAll();
    boolean delete (GroupsAgreements groupsAgreements);
    void GroupAgreementsReport(OutputStream stream) throws IOException;
    
}
