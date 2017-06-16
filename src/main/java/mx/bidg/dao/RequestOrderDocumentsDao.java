package mx.bidg.dao;

import mx.bidg.model.RequestOrderDocuments;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
public interface RequestOrderDocumentsDao extends InterfaceDao<RequestOrderDocuments>{
    RequestOrderDocuments findByIdRequest(Integer idRequest);

}
