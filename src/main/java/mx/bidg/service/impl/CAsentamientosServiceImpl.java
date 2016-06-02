package mx.bidg.service.impl;

import mx.bidg.dao.CAsentamientosDao;
import mx.bidg.model.CAsentamientos;
import mx.bidg.service.CAsentamientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Service
@Transactional
public class CAsentamientosServiceImpl implements CAsentamientosService {

    @Autowired
    CAsentamientosDao dao;

    @Override
    public List<CAsentamientos> findAll() {
        return dao.findAll();
    }

    @Override
    public CAsentamientos findById(Integer idAsentamiento) {
        return dao.findById(idAsentamiento);
    }

    @Override
    public CAsentamientos save(CAsentamientos asentamientos) {
        dao.save(asentamientos);
        return asentamientos;
    }

    @Override
    public CAsentamientos update(CAsentamientos asentamientos) {
        dao.update(asentamientos);
        return asentamientos;
    }

    @Override
    public Boolean delete(CAsentamientos asentamientos) {
        dao.delete(asentamientos);
        return true;
    }

    @Override
    public List<CAsentamientos> findByPostCode(String codigoPostal) {
        return dao.findByPostCode(codigoPostal);
    }
}
