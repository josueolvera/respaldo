package mx.bidg.service;

import mx.bidg.model.CTransactionsStatus;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
public interface CTransactionsStatusService {
    List<CTransactionsStatus> findAll();
}
