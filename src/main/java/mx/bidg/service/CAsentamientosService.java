package mx.bidg.service;

import mx.bidg.model.CAsentamientos;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface CAsentamientosService {
    List<CAsentamientos> findAll();
    CAsentamientos findById(Integer idAsentamiento);
    CAsentamientos save(CAsentamientos asentamientos);
    CAsentamientos update(CAsentamientos asentamientos);
    Boolean delete(CAsentamientos asentamientos);
    List<CAsentamientos> findByPostCode(String codigoPostal);
}
