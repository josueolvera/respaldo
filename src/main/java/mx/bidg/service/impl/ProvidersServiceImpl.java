/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.ProvidersDao;
import mx.bidg.model.Providers;
import mx.bidg.service.ProvidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvidersServiceImpl implements ProvidersService {
    
    @Autowired
    private ProvidersDao dao;

    @Override
    public List<Providers> findAll() {
        return dao.findAll();
    }

    @Override
    public Providers save(Providers providers) {
        dao.save(providers);
        return providers;
    }

    @Override
    public Providers update(Providers providers) {
        return dao.update(providers);
    }

    @Override
    public Providers findById(Integer idProvider) {
        return dao.findById(idProvider);
    }

    @Override
    public Boolean delete(Providers providers) {
        dao.delete(providers);
        return true;
    }
}
