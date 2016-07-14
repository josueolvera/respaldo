package mx.bidg.service;

import mx.bidg.model.CPassengerDocumentsTypes;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface CPassengerDocumentsTypesService {

    List<CPassengerDocumentsTypes> findAll();
    CPassengerDocumentsTypes findById(Integer idPassengerDocumentType);
    CPassengerDocumentsTypes save(CPassengerDocumentsTypes passengerDocumentType);
    CPassengerDocumentsTypes update(CPassengerDocumentsTypes passengerDocumentType);
    Boolean delete(CPassengerDocumentsTypes passengerDocumentType);
}
