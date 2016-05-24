package mx.bidg.service;

import mx.bidg.model.CBalanceStatus;

import java.util.List;

/**
 * Created by jolvera on 23/05/16.
 */
public interface CBalanceStatusService {
    List<CBalanceStatus> findAll();
}
