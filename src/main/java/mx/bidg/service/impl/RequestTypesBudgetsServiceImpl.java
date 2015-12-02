/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.RequestTypesBudgetsDao;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.RequestTypesBudgets;
import mx.bidg.service.RequestTypesBudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class RequestTypesBudgetsServiceImpl implements RequestTypesBudgetsService {
    
    @Autowired
    RequestTypesBudgetsDao dao;

    @Override
    public List<RequestTypesBudgets> findByRequestType(CRequestTypes idRequestType) {
        
        return dao.findByRequestType(idRequestType);
        
    }
    
}
