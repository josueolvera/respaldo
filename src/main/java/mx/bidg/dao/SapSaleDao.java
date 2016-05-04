package mx.bidg.dao;

import mx.bidg.model.SapSale;

/**
 * Created by gerardo8 on 29/04/16.
 */
public interface SapSaleDao extends InterfaceDao<SapSale>  {
    SapSale findByIdSale(String idSale);
}
