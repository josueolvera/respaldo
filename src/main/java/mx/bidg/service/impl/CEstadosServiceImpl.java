package mx.bidg.service.impl;

import mx.bidg.dao.CEstadosDao;
import mx.bidg.model.CEstados;
import mx.bidg.service.CEstadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Service
@Transactional
public class CEstadosServiceImpl implements CEstadosService {

    @Autowired
     private CEstadosDao dao;

    @Override
    public List<CEstados> findAll() {
        return dao.findAll();
    }

}
