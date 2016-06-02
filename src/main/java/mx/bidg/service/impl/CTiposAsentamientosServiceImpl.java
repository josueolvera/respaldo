package mx.bidg.service.impl;

import mx.bidg.dao.CTiposAsentamientosDao;
import mx.bidg.model.CTiposAsentamientos;
import mx.bidg.service.CTiposAsentamientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 2/06/16.
 */
@Service
@Transactional
public class CTiposAsentamientosServiceImpl implements CTiposAsentamientosService {

    @Autowired
    CTiposAsentamientosDao cTiposAsentamientosDao;

    @Override
    public List<CTiposAsentamientos> findAll() {
        return cTiposAsentamientosDao.findAll();
    }

    @Override
    public CTiposAsentamientos findById(Integer idTiposAsentamientos) {
        return cTiposAsentamientosDao.findById(idTiposAsentamientos);
    }

    @Override
    public CTiposAsentamientos save(CTiposAsentamientos cTiposAsentamientos) {
        cTiposAsentamientosDao.save(cTiposAsentamientos);
        return cTiposAsentamientos;
    }

    @Override
    public CTiposAsentamientos update(CTiposAsentamientos cTiposAsentamientos) {
        cTiposAsentamientosDao.update(cTiposAsentamientos);
        return cTiposAsentamientos;
    }

    @Override
    public boolean delete(CTiposAsentamientos cTiposAsentamientos) {
        cTiposAsentamientosDao.delete(cTiposAsentamientos);
        return true;
    }
}
