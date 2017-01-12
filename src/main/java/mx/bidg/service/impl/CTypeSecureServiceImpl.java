package mx.bidg.service.impl;

import mx.bidg.dao.CTypeSecureDao;
import mx.bidg.model.CTypeSecure;
import mx.bidg.service.CTypeSecureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Desarrollador on 12/01/2017.
 */
@Service
@Transactional
public class CTypeSecureServiceImpl implements CTypeSecureService {

    @Autowired
    CTypeSecureDao cTypeSecureDao;

    @Override
    public CTypeSecure save(CTypeSecure cTypeSecure) {
        return cTypeSecureDao.save(cTypeSecure);
    }

    @Override
    public CTypeSecure update(CTypeSecure cTypeSecure) {
        return cTypeSecureDao.update(cTypeSecure);
    }

    @Override
    public CTypeSecure findById(Integer idTypeSecure) {
        return cTypeSecureDao.findById(idTypeSecure);
    }

    @Override
    public List<CTypeSecure> findAll() {
        return cTypeSecureDao.findAll();
    }

    @Override
    public boolean delete(CTypeSecure cTypeSecure) {
        return cTypeSecureDao.delete(cTypeSecure);
    }
}
