package mx.bidg.service.impl;

import mx.bidg.dao.PhoneNumbersDao;
import mx.bidg.model.PhoneNumbers;
import mx.bidg.service.PhoneNumbersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 6/05/16.
 */
@Service
@Transactional
public class PhoneNumbersServiceImpl implements PhoneNumbersService {

    @Autowired
    PhoneNumbersDao dao;

    @Override
    public List<PhoneNumbers> findAll() {
        return dao.findAll();
    }

    @Override
    public PhoneNumbers findById(Integer idPhoneNumbers) {
        return dao.findById(idPhoneNumbers);
    }

    @Override
    public PhoneNumbers save(PhoneNumbers phoneNumbers) {
        dao.save(phoneNumbers);
        return phoneNumbers;
    }

    @Override
    public PhoneNumbers update(PhoneNumbers phoneNumbers) {
        dao.update(phoneNumbers);
        return phoneNumbers;
    }

    @Override
    public Boolean delete(PhoneNumbers phoneNumbers) {
        dao.delete(phoneNumbers);
        return true;
    }
}
