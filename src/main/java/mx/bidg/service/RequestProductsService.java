package mx.bidg.service;

import mx.bidg.model.RequestProducts;

import java.util.List;

/**
 * Created by desarrollador on 30/05/17.
 */
public interface RequestProductsService {
    RequestProducts save (RequestProducts requestProduct);
    RequestProducts update (RequestProducts requestProduct);
    RequestProducts findById (Integer idRequestProduct);
    boolean delete (RequestProducts requestProduct);
    List<RequestProducts> findAll();
    List<RequestProducts> findByIdRequest(Integer idRequest);
}
