package mx.bidg.service;

import mx.bidg.model.Balances;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
public interface BalancesService {
    List<Balances> findAll();
    Balances findById(Integer id);
    Balances save(Balances balance);
    Balances update(Balances balance);
    boolean delete(Balances balance);
}
