package mx.bidg.service.impl;

import mx.bidg.dao.ProviderAddressDao;
import mx.bidg.model.ProviderAddress;
import mx.bidg.model.Providers;
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
