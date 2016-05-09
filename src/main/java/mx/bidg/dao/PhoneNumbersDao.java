package mx.bidg.dao;

import mx.bidg.model.PhoneNumbers;
import mx.bidg.model.Providers;

import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */
public interface PhoneNumbersDao  extends InterfaceDao<PhoneNumbers>{
    List<PhoneNumbers> findByProvider (Providers p);
}
