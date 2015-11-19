package mx.bidg.service;

import mx.bidg.model.CProductTypes;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 19/11/15.
 */
public interface CProductTypesService {
    CProductTypes findById(Integer id);
    List<CProductTypes> findAll();
}
