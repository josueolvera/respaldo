package mx.bidg.service;

import mx.bidg.model.PhoneNumbers;
import mx.bidg.model.Providers;

import javax.validation.constraints.AssertFalse;
import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */
public interface PhoneNumbersService {
    List<PhoneNumbers> findByProvider(Providers provider);
    List<PhoneNumbers> findAll();
    PhoneNumbers findById(Integer idPhoneNumbers);
    PhoneNumbers save(PhoneNumbers phoneNumbers);
    PhoneNumbers update(PhoneNumbers phoneNumbers);
    Boolean delete(PhoneNumbers phoneNumbers);
}
