package mx.bidg.service.impl;

import mx.bidg.dao.CZonaDao;
import mx.bidg.model.CZonas;
import mx.bidg.service.CZonasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
@Service
@Transactional
public class CZonasServiceImpl implements CZonasService {

    @Autowired
    CZonaDao cZonaDao;

    @Override
    public CZonas save(CZonas zona) {
        return cZonaDao.save(zona);
    }

    @Override
    public CZonas update(CZonas zona) {
        return cZonaDao.save(zona);
    }

    @Override
    public CZonas findById(Integer idZona) {
        return cZonaDao.findById(idZona);
    }

    @Override
    public List<CZonas> findAll() {
        return cZonaDao.findAll();
    }

    @Override
    public boolean delete(CZonas zona) {
        cZonaDao.delete(zona);
        return true;
    }
}
