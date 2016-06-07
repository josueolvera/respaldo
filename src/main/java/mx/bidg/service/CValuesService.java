package mx.bidg.service;

import mx.bidg.model.CValues;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
public interface CValuesService {
    List<CValues> findAll();
    CValues save(CValues value);
    List<CValues> findValuesByAttribute(int idAttribute);
}
