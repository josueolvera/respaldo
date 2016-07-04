package mx.bidg.service;

import mx.bidg.model.CEducation;

import java.util.List;

/**
 * Created by jolvera on 22/06/16.
 */
public interface CEducationService {
    List<CEducation> findAll();
    CEducation findById (Integer idEducation);
}
