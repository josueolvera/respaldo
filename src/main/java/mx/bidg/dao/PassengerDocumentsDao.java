package mx.bidg.dao;

import mx.bidg.model.PassengerDocuments;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface PassengerDocumentsDao extends InterfaceDao<PassengerDocuments> {
    List<PassengerDocuments> findByIdDocumentTypeAndIdPassenger(Integer idDocumentType, Integer idPassenger);
}
