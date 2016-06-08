package mx.bidg.dao;

import mx.bidg.model.CValues;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
public interface CValuesDao extends InterfaceDao<CValues> {
    List<CValues> findValuesByAttribute(int idAttribute, int idArticlesCategory);
}
