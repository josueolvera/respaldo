package mx.bidg.service.impl;

import mx.bidg.dao.ProvidersProductsTypesDao;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersProductsTypes;
import mx.bidg.service.ProvidersProductsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 30/05/16.
 */
@Service
@Transactional
public class ProvidersProductsTypesServiceImpl implements ProvidersProductsTypesService {

    @Autowired
    ProvidersProductsTypesDao providersProductsTypesDao;

    @Override
    public ProvidersProductsTypes save(ProvidersProductsTypes providersProductsTypes) {
        return providersProductsTypesDao.save(providersProductsTypes);
    }

    @Override
    public ProvidersProductsTypes update(ProvidersProductsTypes providersProductsTypes) {
        return providersProductsTypesDao.update(providersProductsTypes);
    }

    @Override
    public boolean delete(ProvidersProductsTypes providersProductsTypes) {
        providersProductsTypesDao.delete(providersProductsTypes);
        return true;
    }

    @Override
    public List<ProvidersProductsTypes> findAll() {
        return providersProductsTypesDao.findAll();
    }

    @Override
    public ProvidersProductsTypes findById(int idProvidersProductsTypes) {
        return providersProductsTypesDao.findById(idProvidersProductsTypes);
    }

    @Override
    public List<ProvidersProductsTypes> findByProvider(Providers provider) {
        return providersProductsTypesDao.findByProvider(provider);
    }
}
