package mx.bidg.service;

import mx.bidg.model.PassengerDocuments;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface PassengerDocumentsService {
    List<PassengerDocuments> findAll();
    PassengerDocuments findById(Integer id);
    PassengerDocuments save(PassengerDocuments passengerDocument);
    PassengerDocuments update(PassengerDocuments passengerDocument);
    Boolean delete(PassengerDocuments passengerDocument);
}
