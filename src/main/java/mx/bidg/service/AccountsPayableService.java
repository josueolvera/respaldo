/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.io.IOException;
import java.util.List;
import mx.bidg.model.AccountsPayable;
import mx.bidg.model.CAccountsPayableStatus;

/**
 *
 * @author sistemask
 */
public interface AccountsPayableService {

    List<AccountsPayable> findByFolio(String folio);
    AccountsPayable findById(Integer idAccountPayable);
    List<AccountsPayable> update(String folio, String data) throws Exception;
    List<AccountsPayable> findAll();
    List<AccountsPayable> updatePeriodic(String folio, String data) throws IOException;
    boolean delete(AccountsPayable accountPayable);
    AccountsPayable save(AccountsPayable accountPayable);
    List<AccountsPayable> findAccountsNow();
    List<AccountsPayable> findByReschedule();
    void payAccount(Integer idAccountPayable);
}
