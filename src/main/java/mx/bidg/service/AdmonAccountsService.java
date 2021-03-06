package mx.bidg.service;

import mx.bidg.model.AdmonAccounts;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
public interface AdmonAccountsService {
    List<AdmonAccounts> findAll();
    AdmonAccounts save(AdmonAccounts admonAccount);
    AdmonAccounts update(AdmonAccounts admonAccount);
    AdmonAccounts findById(Integer id);
    boolean delete(AdmonAccounts admonAccount);
}
