package mx.bidg.service.impl;

import mx.bidg.dao.ProvidersContactDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.ProvidersContact;
import mx.bidg.model.Providers;
import mx.bidg.service.ProvidersContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ProvidersContact save(ProvidersContact providersContact) {
        dao.save(providersContact);
        return providersContact;
    }

    @Override
    public ProvidersContact update(ProvidersContact providersContact) {
        dao.update(providersContact);
        return providersContact;
    }

    @Override
    public Boolean delete(ProvidersContact providersContact) {
        if (dao.countContacts(providersContact.getProvider()) <= 1) {
            throw new ValidationException(
                    "No se permite elimanar el ultimo contacto",
                    "No se permite elimanar el ultimo contacto",
                    HttpStatus.FORBIDDEN
            );
        }
        dao.delete(providersContact);
        return true;
    }
}
