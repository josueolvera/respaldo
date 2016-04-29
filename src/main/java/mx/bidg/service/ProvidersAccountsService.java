/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.Accounts;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;

/**
 *
 * @author kenneth
 */
public interface ProvidersAccountsService {
    
    public List<ProvidersAccounts> findByProvider(Providers provider);
    public ProvidersAccounts save(String data) throws Exception;
    ProvidersAccounts save(ProvidersAccounts providersAccounts);
    public List<ProvidersAccounts> findByAccountsProvider(Accounts a);
    
}
