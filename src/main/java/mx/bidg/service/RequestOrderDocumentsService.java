package mx.bidg.service;

import mx.bidg.model.RequestOrderDocuments;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
public interface RequestOrderDocumentsService {
    List<RequestOrderDocuments> findAll();
    RequestOrderDocuments findById(Integer idROD);
    RequestOrderDocuments save(RequestOrderDocuments rod);
    RequestOrderDocuments update(RequestOrderDocuments rod);
    boolean delete(RequestOrderDocuments rod);
    RequestOrderDocuments findByIdRequest(Integer idRequest);

}
