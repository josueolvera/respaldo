package mx.bidg.dao;

import mx.bidg.model.ProviderAddress;
import mx.bidg.model.Providers;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface ProviderAddressDao extends InterfaceDao<ProviderAddress> {
    Long countAddress(Providers provider);
    List<ProviderAddress> findByProvider (Providers p);
    ProviderAddress findByIdProvider(Integer idProvider);
}
