package mx.bidg.service;

import mx.bidg.model.Balances;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
public interface BalancesService {
    List<Balances> findAll();
}
