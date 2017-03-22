package mx.bidg.service.impl;

import mx.bidg.dao.ConciliationFileDao;
import mx.bidg.model.ConciliationFile;
import mx.bidg.service.ConciliationFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Desarrollador on 27/02/2017.
 */
@Service
@Transactional
public class ConciliationFileServiceImpl implements ConciliationFileService {

    @Autowired
    ConciliationFileDao conciliationFileDao;

    @Override
    public ConciliationFile save(ConciliationFile conciliationFile) {
        return conciliationFileDao.save(conciliationFile);
    }

    @Override
    public ConciliationFile update(ConciliationFile conciliationFile) {
        return conciliationFileDao.update(conciliationFile);
    }

    @Override
    public ConciliationFile findById(Integer idConciliationFile) {
        return conciliationFileDao.findById(idConciliationFile);
    }

    @Override
    public List<ConciliationFile> findAll() {
        return conciliationFileDao.findAll();
    }

    @Override
    public boolean delete(ConciliationFile conciliationFile) {
        conciliationFileDao.delete(conciliationFile);
        return true;
    }
}
