package mx.bidg.service.impl;

import mx.bidg.dao.SinisterTruckdriverDao;
import mx.bidg.model.SinisterTruckdriver;
import mx.bidg.service.SinisterTruckdriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
@Service
@Transactional
public class SinisterTruckdriverServiceImpl implements SinisterTruckdriverService {
    @Autowired
    SinisterTruckdriverDao sinisterTruckdriverDao;

    @Override
    public SinisterTruckdriver save(SinisterTruckdriver sinisterTruckdriver) {
        return sinisterTruckdriverDao.save(sinisterTruckdriver);
    }

    @Override
    public SinisterTruckdriver update(SinisterTruckdriver sinisterTruckdriver) {
        return sinisterTruckdriverDao.update(sinisterTruckdriver);
    }
    @Override
    public SinisterTruckdriver findByid(Integer idST) {
        return sinisterTruckdriverDao.findById(idST);
    }

    @Override
    public List<SinisterTruckdriver> findAll() {
        return sinisterTruckdriverDao.findAll();
    }
    @Override
    public boolean delete(SinisterTruckdriver sinisterTruckdriver) {
        sinisterTruckdriverDao.delete(sinisterTruckdriver);
        return true;
    }

    @Override
    public List<SinisterTruckdriver> findByDateStart(int idTipeAssistance, String startDate) {
        return sinisterTruckdriverDao.findByDateStart(idTipeAssistance,startDate);
    }
}
