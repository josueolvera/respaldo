package mx.bidg.service.impl;

import mx.bidg.dao.CalculationRolesDao;
import mx.bidg.model.CalculationRoles;
import mx.bidg.service.CalculationRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class CalculationRolesServiceImpl implements CalculationRolesService {

    @Autowired
    CalculationRolesDao calculationRolesDao;

    @Override
    public CalculationRoles save(CalculationRoles calculationRoles) {
        return calculationRolesDao.save(calculationRoles);
    }

    @Override
    public CalculationRoles update(CalculationRoles calculationRoles) {
        return calculationRolesDao.update(calculationRoles);
    }

    @Override
    public CalculationRoles findById(Integer idCalculationRole) {
        return calculationRolesDao.findById(idCalculationRole);
    }

    @Override
    public boolean delete(CalculationRoles calculationRoles) {
        calculationRolesDao.delete(calculationRoles);
        return true;
    }

    @Override
    public List<CalculationRoles> findAll() {
        return calculationRolesDao.findAll();
    }
}
