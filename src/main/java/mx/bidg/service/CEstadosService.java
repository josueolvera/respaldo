package mx.bidg.service;

import mx.bidg.model.CEstados;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface CEstadosService {
    List<CEstados> findAll();
    CEstados findById(Integer idEstados);
    CEstados save(CEstados estados);
    CEstados update(CEstados estados);
    Boolean delete(CEstados estados);
}
