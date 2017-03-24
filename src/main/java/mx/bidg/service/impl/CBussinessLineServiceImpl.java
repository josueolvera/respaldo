package mx.bidg.service.impl;

import mx.bidg.dao.CBussinessLineDao;
import mx.bidg.model.CBussinessLine;
import mx.bidg.service.CBussinessLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 21/03/2017.
 */
@Service
@Transactional
public class CBussinessLineServiceImpl implements CBussinessLineService{

    @Autowired
    private CBussinessLineDao cBussinessLineDao;

    @Override
    public List<CBussinessLine> findAll() {
        return cBussinessLineDao.findAll();
    }

    @Override
    public CBussinessLine findById(Integer idBusinessLine) {
        return cBussinessLineDao.findById(idBusinessLine);
    }

    @Override
    public CBussinessLine save(CBussinessLine cBussinessLine) {
        return cBussinessLineDao.save(cBussinessLine);
    }

    @Override
    public CBussinessLine update(CBussinessLine cBussinessLine) {
        return cBussinessLineDao.update(cBussinessLine);
    }

    @Override
    public boolean delete(CBussinessLine cBussinessLine) {
        return cBussinessLineDao.delete(cBussinessLine);
    }
}
