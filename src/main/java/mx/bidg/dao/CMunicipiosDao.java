package mx.bidg.dao;

import mx.bidg.model.CEstados;
import mx.bidg.model.CMunicipios;

/**
 * Created by jolvera on 7/05/16.
 */
public interface CMunicipiosDao extends InterfaceDao<CMunicipios> {
    CMunicipios findMunicipio(CEstados e,int idMunicipios);
}
