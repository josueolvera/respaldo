package mx.bidg.dao;

import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersContact;

import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */
public interface ProvidersContactDao extends InterfaceDao<ProvidersContact>{
    Long countContacts(Providers provider);
    List<ProvidersContact> findByProvider (Providers p);
    ProvidersContact findByIdProvider(Integer idProvider);
}
