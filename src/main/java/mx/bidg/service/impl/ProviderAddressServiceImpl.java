package mx.bidg.service.impl;

import mx.bidg.dao.CAsentamientosDao;
import mx.bidg.dao.CEstadosDao;
import mx.bidg.dao.CMunicipiosDao;
import mx.bidg.dao.ProviderAddressDao;
import mx.bidg.model.*;
import mx.bidg.service.ProviderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Service
@Transactional
public class ProviderAddressServiceImpl implements ProviderAddressService {

    @Autowired
    ProviderAddressDao dao;

    @Autowired
    CAsentamientosDao cAsentamientosDao;

    @Autowired
    CMunicipiosDao cMunicipiosDao;

    @Autowired
    CEstadosDao cEstadosDao;

    @Override
    public List<ProviderAddress> findByProvider(Providers provider) {
        List<ProviderAddress> providerAddress = dao.findByProvider(provider);
        for(ProviderAddress providerAddress1 : providerAddress) {
            CAsentamientos asentamiento = providerAddress1.getAsentamiento();
            CMunicipios municipio = cMunicipiosDao.findMunicipio(
                    new CEstados(asentamiento.getIdEstado()), asentamiento.getIdMunicipio()
            );
            asentamiento.setMunicipios(municipio);
        }
        return providerAddress;
    }

    @Override
    public List<ProviderAddress> findAll() {
        return dao.findAll();
    }

    @Override
    public ProviderAddress findById(Integer idProviderAddress) {
        return dao.findById(idProviderAddress);
    }

    @Override
    public ProviderAddress save(ProviderAddress providerAddress) {
        dao.save(providerAddress);
        return providerAddress;
    }

    @Override
    public ProviderAddress update(ProviderAddress providerAddress) {
        dao.update(providerAddress);
        return providerAddress;
    }

    @Override
    public Boolean delete(ProviderAddress providerAddress) {
        dao.delete(providerAddress);
        return true;
    }
}
