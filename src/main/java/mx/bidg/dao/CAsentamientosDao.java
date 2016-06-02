package mx.bidg.dao;

import mx.bidg.model.CAsentamientos;
import mx.bidg.model.CEstados;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface CAsentamientosDao extends InterfaceDao<CAsentamientos> {
    List<CAsentamientos> findByPostCode (String codigoPostal);
}
