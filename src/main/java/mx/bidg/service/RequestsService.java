/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.HashMap;
import java.util.List;

import mx.bidg.model.EmailTemplates;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface RequestsService {

    HashMap<String, Object> getBudgetMonthProductType(String data) throws Exception;

    Requests saveData(String data, Users user) throws Exception;

    Requests authorization(Integer idRequest);

    Requests findById(Integer idRequest);

    Requests findByFolio(String folio);

    EmailTemplates sendEmailForNewRequest(Requests request);

    EmailTemplates sendEmailForNewRequestAuthorization(Requests request, Users user);

    Requests changeActiveStatus(Integer idRequest);

    List<Requests> findByRequestCategory(Integer idRequestCategory);

    List<Requests> findByCategoryAndTypeByEmployee(Integer idRequestCategory, Integer idRequestType, Integer idEmployee);

    List<Requests> findByCategoryAndType(Integer idRequestCategory, Integer idRequestType);

    boolean deleteRequest(Integer idRequest);

    List<Requests> findByCategoryAndTypeAndStatus(Integer idRequestCategory, Integer idRequestType);

    Requests rejectRequest(Integer idRequest, String rejectJustify, Users user);
    Requests update(Requests request);


    List<Requests> findByDCC(List<Integer> idDCCs);
    List<Requests> findAll();
    Requests payRequest(Integer idRequest);
    List<Requests> findListByFolio(String folio);
    List<Requests> findByStatus(Integer idRequestStatus);
}
