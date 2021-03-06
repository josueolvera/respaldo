/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CAreasDao;
import mx.bidg.model.CAreas;
import mx.bidg.service.CAreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sistemask
 */
@Service
@Transactional
public class CAreasServiceImpl implements CAreasService {
    
    @Autowired
    CAreasDao dao;

    @Override
    public List<CAreas> findAll() {
        return dao.findAll();
    }

    @Override
    public CAreas findById(Integer idArea) {
        return dao.findById(idArea);
    }

    @Override
    public CAreas findAreaWhitRole(Integer idArea) {
        return dao.findAreaWithRoles(idArea);
    }

    @Override
    public List<CAreas> findBySaemFlag(Integer idArea,Integer saemFlag) {
        return dao.findBySaemFlag(idArea, saemFlag);
    }

    @Override
    public CAreas save(CAreas area) {
        return dao.save(area);
    }

    @Override
    public CAreas update(CAreas area) {
        return dao.update(area);
    }

    @Override
    public boolean delete(CAreas area) {
        return dao.delete(area);
    }

}
