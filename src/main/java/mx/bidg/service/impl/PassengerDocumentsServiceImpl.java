package mx.bidg.service.impl;

import mx.bidg.dao.PassengerDocumentsDao;
import mx.bidg.model.PassengerDocuments;
import mx.bidg.service.PassengerDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public class PassengerDocumentsServiceImpl implements PassengerDocumentsService {

    @Autowired
    private PassengerDocumentsDao passengerDocumentsDao;

    @Override
    public List<PassengerDocuments> findAll() {
        return passengerDocumentsDao.findAll();
    }

    @Override
    public PassengerDocuments findById(Integer id) {
        return passengerDocumentsDao.findById(id);
    }

    @Override
    public PassengerDocuments save(PassengerDocuments passengerDocument) {
        return passengerDocumentsDao.save(passengerDocument);
    }

    @Override
    public PassengerDocuments update(PassengerDocuments passengerDocument) {
        return passengerDocumentsDao.update(passengerDocument);
    }

    @Override
    public Boolean delete(PassengerDocuments passengerDocument) {
        return passengerDocumentsDao.delete(passengerDocument);
    }
}
