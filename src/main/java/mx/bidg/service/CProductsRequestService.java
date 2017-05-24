package mx.bidg.service;

import mx.bidg.model.CProductsRequest;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
public interface CProductsRequestService {
    List<CProductsRequest> findAll();
    CProductsRequest findById(int id);
}
