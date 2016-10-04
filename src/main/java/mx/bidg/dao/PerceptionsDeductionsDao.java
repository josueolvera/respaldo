package mx.bidg.dao;

import mx.bidg.model.PerceptionsDeductions;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface PerceptionsDeductionsDao extends InterfaceDao<PerceptionsDeductions> {
    List<PerceptionsDeductions> findAllWithStatus();
}
