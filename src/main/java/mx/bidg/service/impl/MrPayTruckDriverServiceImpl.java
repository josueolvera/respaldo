package mx.bidg.service.impl;

import mx.bidg.dao.MrPayTruckDriverDao;
import mx.bidg.model.MrPayTruckDriver;
import mx.bidg.service.MrPayTruckDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 19/01/2017.
 */
@Transactional
@Service
public class MrPayTruckDriverServiceImpl implements MrPayTruckDriverService{

    @Autowired
    private MrPayTruckDriverDao mrPayTruckDriverDao;

    @Override
    public MrPayTruckDriver findById(Integer id) {
        return mrPayTruckDriverDao.findById(id);
    }

    @Override
    public List<MrPayTruckDriver> findAll() {
        return mrPayTruckDriverDao.findAll();
    }

    @Override
    public MrPayTruckDriver save(MrPayTruckDriver mrPayTruckDriver) {
        return mrPayTruckDriverDao.save(mrPayTruckDriver);
    }

    @Override
    public MrPayTruckDriver update(MrPayTruckDriver mrPayTruckDriver) {
        return mrPayTruckDriverDao.update(mrPayTruckDriver);
    }

    @Override
    public boolean delete(MrPayTruckDriver mrPayTruckDriver) {
        return mrPayTruckDriverDao.delete(mrPayTruckDriver);
    }
}
