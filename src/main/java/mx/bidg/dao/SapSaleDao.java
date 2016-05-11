package mx.bidg.dao;

import mx.bidg.model.SapSale;

import java.util.List;

/**
 * Created by gerardo8 on 29/04/16.
 */
public interface SapSaleDao extends InterfaceDao<SapSale>  {
    List<SapSale> findAllByIdSale(String idSale);
    SapSale findByIdSale(String idSale);
}
