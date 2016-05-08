package mx.bidg.service.impl;

import mx.bidg.dao.CMunicipalitiesDao;
import mx.bidg.model.CMunicipalities;
import mx.bidg.service.CMunicipalitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Service
@Transactional
public class CMunicipalitiesServiceImpl implements CMunicipalitiesService {

    @Autowired
    CMunicipalitiesDao dao;

    @Override
    public List<CMunicipalities> findAll() {
        return dao.findAll();
    }

    @Override
    public CMunicipalities findById(Integer idMunicipality) {
        return dao.findById(idMunicipality);
    }

    @Override
    public CMunicipalities save(CMunicipalities municipality) {
        dao.save(municipality);
        return municipality;
    }

    @Override
    public CMunicipalities update(CMunicipalities municipality) {
        dao.update(municipality);
        return municipality;
    }

    @Override
    public Boolean delete(CMunicipalities municipality) {
        dao.delete(municipality);
        return true;
    }
}
