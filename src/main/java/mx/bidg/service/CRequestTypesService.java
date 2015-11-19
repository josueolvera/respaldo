package mx.bidg.service;

import mx.bidg.model.CRequestTypes;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 18/11/15.
 */
public interface CRequestTypesService {
    public List<CRequestTypes> findAll();
    public CRequestTypes findById(Integer id);
}
