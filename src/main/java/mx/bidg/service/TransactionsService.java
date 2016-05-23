package mx.bidg.service;

import mx.bidg.model.Transactions;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
public interface TransactionsService {
    Transactions findById(Integer id);
    Transactions save(Transactions transaction);
    Transactions update(Transactions transaction);
    boolean delete(Transactions transaction);
    List<Transactions> findAll();
}
