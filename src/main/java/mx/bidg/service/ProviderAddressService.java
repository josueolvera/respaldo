package mx.bidg.service;

import mx.bidg.model.ProviderAddress;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
public interface ProviderAddressService {
    List<ProviderAddress> findAll();
    ProviderAddress findById(Integer idProviderAddress);
    ProviderAddress save(ProviderAddress providerAddress);
    ProviderAddress update(ProviderAddress providerAddress);
    Boolean delete(ProviderAddress providerAddress);
}
