package mx.bidg.service.impl;

import mx.bidg.dao.DistributorsEmployeeDocumentsDao;
import mx.bidg.model.DitributorsEmployeeDocuments;
import mx.bidg.service.DistributorsEmployeeDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
@Service
@Transactional
public class DistributorsEmployeeDocumentsServiceImpl implements DistributorsEmployeeDocumentsService {

    @Autowired
    DistributorsEmployeeDocumentsDao distributorsEmployeeDocumentsDao;

    @Override
    public DitributorsEmployeeDocuments save(DitributorsEmployeeDocuments distributorsEmployeeDocuments) {
        return distributorsEmployeeDocumentsDao.save(distributorsEmployeeDocuments);
    }

    @Override
    public DitributorsEmployeeDocuments update(DitributorsEmployeeDocuments ditributorsEmployeeDocuments) {
        return distributorsEmployeeDocumentsDao.update(ditributorsEmployeeDocuments);
    }

    @Override
    public DitributorsEmployeeDocuments findById(Integer idDitributorsEmployeeDocuments) {
        return distributorsEmployeeDocumentsDao.findById(idDitributorsEmployeeDocuments);
    }

    @Override
    public List<DitributorsEmployeeDocuments> findAll() {
        return distributorsEmployeeDocumentsDao.findAll();
    }

    @Override
    public boolean delete(DitributorsEmployeeDocuments ditributorsEmployeeDocuments) {
        distributorsEmployeeDocumentsDao.delete(ditributorsEmployeeDocuments);
        return true;
    }
}
