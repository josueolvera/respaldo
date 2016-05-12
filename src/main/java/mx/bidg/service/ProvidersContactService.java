package mx.bidg.service;

import mx.bidg.model.ProvidersContact;
import mx.bidg.model.Providers;

import javax.validation.constraints.AssertFalse;
import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */
public interface ProvidersContactService {
    List<ProvidersContact> findByProvider(Providers provider);
    List<ProvidersContact> findAll();
    ProvidersContact findById(Integer idProvidersContact);
    ProvidersContact save(ProvidersContact ProvidersContact);
    ProvidersContact update(ProvidersContact ProvidersContact);
    Boolean delete(ProvidersContact ProvidersContact);
}
