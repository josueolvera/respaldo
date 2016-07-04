package mx.bidg.service.impl;

import mx.bidg.dao.CBanksDao;
import mx.bidg.model.CBanks;
import mx.bidg.service.CBanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 13/04/2016.
 */
@Service
@Transactional
public class CBanksServiceImp implements CBanksService {

    @Autowired
    private CBanksDao banksDao;

    @Override
    public List<CBanks> findAll() {
        return banksDao.findAll();
    }

    @Override
    public CBanks findById(Integer idBank) {
        return banksDao.findById(idBank);
    }
}
