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
    List<ProvidersAccounts> findByProvider(Providers provider);
    ProvidersAccounts save(String data) throws Exception;
    ProvidersAccounts findByAccountsProvider(Accounts a);
    Boolean delete(ProvidersAccounts providersAccounts);
    ProvidersAccounts save(ProvidersAccounts providersAccount);
}
