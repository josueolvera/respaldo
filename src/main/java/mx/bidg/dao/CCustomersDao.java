package mx.bidg.dao;

import mx.bidg.model.CCustomers;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/12/2016.
 */
public interface CCustomersDao extends InterfaceDao<CCustomers> {
    List<CCustomers>findAll();
}
