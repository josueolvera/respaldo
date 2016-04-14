package mx.bidg.service;

import mx.bidg.model.Transactions;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
public interface TransactionsService {
    List<Transactions> findAll();
}
