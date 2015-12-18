/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.Requests;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestsServiceImpl implements RequestsService {
    
    @Autowired
    RequestsDao requestsDao;
    
    ObjectMapper map = new ObjectMapper();

    @Override
    public Requests save(String data) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);
        
        return null;
    }
    
}
