package mx.bidg.service.impl;

import mx.bidg.dao.ProvidersContactDao;
import mx.bidg.model.ProvidersContact;
import mx.bidg.model.Providers;
import mx.bidg.service.ProvidersContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */
@Service
@Transactional
public class ProvidersContactServiceImpl implements ProvidersContactService {

    @Autowired
    ProvidersContactDao dao;

    @Override
    public List<ProvidersContact> findByProvider(Providers provider) {
        return dao.findByProvider(provider);
    }

    @Override
    public List<ProvidersContact> findAll() {
        return dao.findAll();
    }

    @Override
    public ProvidersContact findById(Integer idProvidersContact) {
        return dao.findById(idProvidersContact);
    }

    @Override
    public ProvidersContact save(ProvidersContact ProvidersContact) {
        dao.save(ProvidersContact);
        return ProvidersContact;
    }

    @Override
    public ProvidersContact update(ProvidersContact ProvidersContact) {
        dao.update(ProvidersContact);
        return ProvidersContact;
    }

    @Override
    public Boolean delete(ProvidersContact ProvidersContact) {
        dao.delete(ProvidersContact);
        return true;
    }
}
