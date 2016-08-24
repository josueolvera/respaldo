package mx.bidg.service;

import mx.bidg.model.CZonas;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
public interface CZonasService {
    CZonas save(CZonas zona);
    CZonas update(CZonas zona);
    CZonas findById(Integer idZona);
    List<CZonas> findAll();
    boolean delete(CZonas zona);
    List<CZonas> findBySaemFlag(Integer idZonas, Integer saemFlag);
}
