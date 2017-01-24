package mx.bidg.service;

import mx.bidg.model.CTypeMethodPay;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
public interface CTypeMethodPayService {
    CTypeMethodPay findById (Integer id);
    List<CTypeMethodPay> findAll();
    CTypeMethodPay save (CTypeMethodPay cTypeMethodPay);
    boolean delete (CTypeMethodPay cTypeMethodPay);
    CTypeMethodPay update (CTypeMethodPay cTypeMethodPay);
}
