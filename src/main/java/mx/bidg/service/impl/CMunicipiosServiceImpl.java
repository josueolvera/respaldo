package mx.bidg.service.impl;

import mx.bidg.dao.CMunicipiosDao;
import mx.bidg.model.CEstados;
import mx.bidg.model.CMunicipios;
import mx.bidg.service.CMunicipiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Service
@Transactional
public class CMunicipiosServiceImpl implements CMunicipiosService {

    @Autowired
    CMunicipiosDao dao;

    @Override
    public List<CMunicipios> findAll() {
        return dao.findAll();
    }

    @Override
    public CMunicipios findById(Integer idMunicipios) {
        return dao.findById(idMunicipios);
    }

    @Override
    public CMunicipios save(CMunicipios municipio) {
        dao.save(municipio);
        return municipio;
    }

    @Override
    public CMunicipios update(CMunicipios municipio) {
        dao.update(municipio);
        return municipio;
    }

    @Override
    public CMunicipios findMunicipio(CEstados e, int idMunicipios) {
        CMunicipios municipio = dao.findMunicipio(e,idMunicipios);
        return municipio;
    }

    @Override
    public Boolean delete(CMunicipios municipio) {
        dao.delete(municipio);
        return true;
    }
}
