package mx.bidg.service.impl;

import mx.bidg.dao.CalculationReportDao;
import mx.bidg.model.CalculationReport;
import mx.bidg.service.CalculationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
@Service
@Transactional
public class CalculationReportServiceImpl implements CalculationReportService {

    @Autowired
    CalculationReportDao calculationReportDao;

    @Override
    public CalculationReport save(CalculationReport calculationReport) {
        return calculationReportDao.save(calculationReport);
    }

    @Override
    public CalculationReport update(CalculationReport calculationReport) {
        return calculationReportDao.update(calculationReport);
    }

    @Override
    public CalculationReport findById(Integer idCalculationReport) {
        return calculationReportDao.findById(idCalculationReport);
    }

    @Override
    public List<CalculationReport> findAll() {
        return calculationReportDao.findAll();
    }

    @Override
    public boolean delete(CalculationReport calculationReport) {
        calculationReportDao.delete(calculationReport);
        return true;
    }
}
