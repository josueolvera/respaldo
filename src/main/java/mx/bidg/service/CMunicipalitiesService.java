package mx.bidg.service;

import mx.bidg.model.CMunicipalities;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface CMunicipalitiesService {
    List<CMunicipalities> findAll();
    CMunicipalities findById(Integer idMunicipality);
    CMunicipalities save(CMunicipalities municipality);
    CMunicipalities update(CMunicipalities municipality);
    Boolean delete(CMunicipalities municipality);
}
