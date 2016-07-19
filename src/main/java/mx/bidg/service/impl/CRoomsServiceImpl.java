/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CRoomsDao;
import mx.bidg.model.CRooms;
import mx.bidg.service.CRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CRoomsServiceImpl implements CRoomsService {
    
    @Autowired
    CRoomsDao cRoomsDao;

    @Override
    public List<CRooms> findAll() {
       return cRoomsDao.findAll();
    }

    @Override
    public CRooms findById(Integer idRoom) {
        return cRoomsDao.findById(idRoom);
    }

    @Override
    public CRooms save(CRooms cRooms) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CRooms update(CRooms cRooms) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean delete(CRooms cRooms) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
