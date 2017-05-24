package mx.bidg.dao;

import mx.bidg.model.CProductsRequest;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
public interface CProductsRequestDao extends InterfaceDao<CProductsRequest>{
    List<CProductsRequest> findAll();
    CProductsRequest findById(int idProductRequest);
}
