package mx.bidg.dao;

import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersProductsTypes;

import java.util.List;

/**
 * Created by jolvera on 30/05/16.
 */
public interface ProvidersProductsTypesDao extends InterfaceDao<ProvidersProductsTypes> {

    List<ProvidersProductsTypes> findByProvider (Providers p);
}
