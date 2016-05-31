package mx.bidg.service;

import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersProductsTypes;

import java.util.List;

/**
 * Created by jolvera on 30/05/16.
 */
public interface ProvidersProductsTypesService {

    ProvidersProductsTypes save(ProvidersProductsTypes providersProductsTypes);
    ProvidersProductsTypes update(ProvidersProductsTypes providersProductsTypes);
    boolean delete(ProvidersProductsTypes providersProductsTypes);
    List<ProvidersProductsTypes> findAll();
    ProvidersProductsTypes findById(int idProvidersProductsTypes);
    List<ProvidersProductsTypes> findByProvider(Providers provider);
}