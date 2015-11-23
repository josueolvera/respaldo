package mx.bidg.service;

import mx.bidg.model.CProducts;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
public interface CProductsService {
    CProducts findById(int id);
    List<CProducts> findAll();
}
