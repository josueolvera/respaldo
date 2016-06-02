package mx.bidg.service;

import mx.bidg.model.CEstados;
import mx.bidg.model.CMunicipios;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface CMunicipiosService {
    List<CMunicipios> findAll();
    CMunicipios findById(Integer idMunicipios);
    CMunicipios save(CMunicipios municipios);
    CMunicipios update(CMunicipios municipios);
    CMunicipios findMunicipio(CEstados e, int idMunicipios);
    Boolean delete(CMunicipios municipios);
}
