package mx.bidg.service.impl;

import mx.bidg.dao.RequestOrderDocumentsDao;
import mx.bidg.model.RequestOrderDocuments;
import mx.bidg.service.RequestOrderDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by g_on_ on 22/05/2017.
 */
@Service
@Transactional
public class RequestOrderDocumentsServiceImpl implements RequestOrderDocumentsService{

    @Autowired
    private RequestOrderDocumentsDao requestOrderDocumentsDao;

    @Override
    public List<RequestOrderDocuments> findAll() {
        return requestOrderDocumentsDao.findAll();
    }

    @Override
    public RequestOrderDocuments findById(Integer idOD) {
        return requestOrderDocumentsDao.findById(idOD);
    }

    @Override
    public RequestOrderDocuments save(RequestOrderDocuments rod) {
        return requestOrderDocumentsDao.save(rod);
    }

    @Override
    public RequestOrderDocuments update(RequestOrderDocuments rod) {
        return requestOrderDocumentsDao.update(rod);
    }

    @Override
    public boolean delete(RequestOrderDocuments rod) {
        return requestOrderDocumentsDao.delete(rod);
    }

}
