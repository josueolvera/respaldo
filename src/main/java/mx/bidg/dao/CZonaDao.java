package mx.bidg.dao;

import mx.bidg.model.CZonas;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
public interface CZonaDao extends InterfaceDao <CZonas> {
    List<CZonas> findBySaemFlag(Integer idZonas, Integer saemFlag);
}
