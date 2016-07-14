package mx.bidg.service.impl;

import mx.bidg.dao.CPassengerDocumentsTypesDao;
import mx.bidg.model.CPassengerDocumentsTypes;
import mx.bidg.service.CPassengerDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@Service
@Transactional
public class CPassengerDocumentsTypesServiceImpl implements CPassengerDocumentsTypesService {

    @Autowired
    private CPassengerDocumentsTypesDao cPassengerDocumentsTypesDao;

    @Override
    public List<CPassengerDocumentsTypes> findAll() {
        return cPassengerDocumentsTypesDao.findAll();
    }

    @Override
    public CPassengerDocumentsTypes findById(Integer idPassengerDocumentType) {
        return cPassengerDocumentsTypesDao.findById(idPassengerDocumentType);
    }

    @Override
    public CPassengerDocumentsTypes save(CPassengerDocumentsTypes passengerDocumentType) {
        return cPassengerDocumentsTypesDao.save(passengerDocumentType);
    }

    @Override
    public CPassengerDocumentsTypes update(CPassengerDocumentsTypes passengerDocumentType) {
        return cPassengerDocumentsTypesDao.update(passengerDocumentType);
    }

    @Override
    public Boolean delete(CPassengerDocumentsTypes passengerDocumentType) {
        return cPassengerDocumentsTypesDao.delete(passengerDocumentType);
    }
}
