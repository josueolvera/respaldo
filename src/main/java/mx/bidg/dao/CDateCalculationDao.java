package mx.bidg.dao;

import mx.bidg.model.CDateCalculation;

import java.util.List;

/**
 * Created by josueolvera on 6/10/16.
 */
public interface CDateCalculationDao extends InterfaceDao<CDateCalculation>{
    List<CDateCalculation> findByStatus(Integer status);
}
