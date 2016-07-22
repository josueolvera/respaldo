package mx.bidg.service.impl;

import mx.bidg.dao.CGendersDao;
import mx.bidg.model.CGenders;
import mx.bidg.service.CGendersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
@Service
@Transactional
public class CGendersServiceImpl implements CGendersService {

    @Autowired
    CGendersDao cGendersDao;

    @Override
    public CGenders findById(Integer idGender) {
        return cGendersDao.findById(idGender);
    }

    @Override
    public List<CGenders> findAll() {
        return cGendersDao.findAll();
    }

    @Override
    public CGenders findByGenderName(String genderName) {
        return cGendersDao.findByGenderName(genderName);
    }
}
