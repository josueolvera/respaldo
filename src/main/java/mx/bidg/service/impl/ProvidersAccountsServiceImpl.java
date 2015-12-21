/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.ProvidersAccountsDao;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;
import mx.bidg.service.ProvidersAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvidersAccountsServiceImpl implements ProvidersAccountsService {
    
    @Autowired
    ProvidersAccountsDao dao;

    @Override
    public List<ProvidersAccounts> findByProvider(Providers provider) {
        return dao.findByProvider(provider);
    }

    @Override
    public ProvidersAccounts save(String data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
