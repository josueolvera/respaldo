package mx.bidg.service.impl;

import mx.bidg.dao.BonusCommisionableEmployeeDao;
import mx.bidg.model.BonusCommisionableEmployee;
import mx.bidg.service.BonusCommisionableEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josue on 11/10/2016.
 */
@Service
@Transactional
public class BonusCommisionableEmployeeServiceImpl implements BonusCommisionableEmployeeService {

    @Autowired
    BonusCommisionableEmployeeDao bonusCommisionableEmployeeDao;

    @Override
    public BonusCommisionableEmployee save(BonusCommisionableEmployee bonusCommisionableEmployee) {
        return bonusCommisionableEmployeeDao.save(bonusCommisionableEmployee);
    }

    @Override
    public BonusCommisionableEmployee update(BonusCommisionableEmployee bonusCommisionableEmployee) {
        return bonusCommisionableEmployeeDao.update(bonusCommisionableEmployee);
    }

    @Override
    public BonusCommisionableEmployee findById(Integer idBonusCommisionableEmployee) {
        return bonusCommisionableEmployeeDao.findById(idBonusCommisionableEmployee);
    }

    @Override
    public List<BonusCommisionableEmployee> findAll() {
        return bonusCommisionableEmployeeDao.findAll();
    }

    @Override
    public boolean delete(BonusCommisionableEmployee bonusCommisionableEmployee) {
        bonusCommisionableEmployeeDao.delete(bonusCommisionableEmployee);
        return true;
    }
}
