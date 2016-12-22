package mx.bidg.service;

import mx.bidg.model.CCustomers;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/12/2016.
 */
public interface CCustomersService {
    List<CCustomers>findAll();
    CCustomers findById(Integer id);
}
