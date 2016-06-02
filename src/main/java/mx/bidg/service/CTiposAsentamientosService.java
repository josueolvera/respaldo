package mx.bidg.service;

import mx.bidg.model.CTiposAsentamientos;

import java.util.List;

/**
 * Created by jolvera on 2/06/16.
 */
public interface CTiposAsentamientosService {
    List<CTiposAsentamientos> findAll ();
    CTiposAsentamientos findById(Integer idTiposAsentamientos);
    CTiposAsentamientos save(CTiposAsentamientos cTiposAsentamientos);
    CTiposAsentamientos update(CTiposAsentamientos cTiposAsentamientos);
    boolean delete(CTiposAsentamientos cTiposAsentamientos);
}
