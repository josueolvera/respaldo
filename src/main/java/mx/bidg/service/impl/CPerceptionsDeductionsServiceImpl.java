package mx.bidg.service.impl;

import mx.bidg.dao.CPerceptionsDeductionsDao;
import mx.bidg.dao.DistributorPerceptionDeductionDao;
import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.DistributorPerceptionDeduction;
import mx.bidg.service.CPerceptionsDeductionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Repository
@Transactional
public class CPerceptionsDeductionsServiceImpl implements CPerceptionsDeductionsService {

    @Autowired
    CPerceptionsDeductionsDao cPerceptionsDeductionsDao;

    @Autowired
    DistributorPerceptionDeductionDao distributorPerceptionDeductionDao;

    @Override
    public CPerceptionsDeductions save(CPerceptionsDeductions cPerceptionsDeductions) {
        return cPerceptionsDeductionsDao.save(cPerceptionsDeductions);
    }

    @Override
    public CPerceptionsDeductions update(CPerceptionsDeductions cPerceptionsDeductions) {
        return cPerceptionsDeductionsDao.update(cPerceptionsDeductions);
    }

    @Override
    public CPerceptionsDeductions findById(Integer idCPd) {
        return cPerceptionsDeductionsDao.findById(idCPd);
    }

    @Override
    public List<CPerceptionsDeductions> findAll() {
        return cPerceptionsDeductionsDao.findAll();
    }

    @Override
    public boolean delete(CPerceptionsDeductions cPerceptionsDeductions) {
        cPerceptionsDeductionsDao.delete(cPerceptionsDeductions);
        return true;
    }

    @Override
    public List<CPerceptionsDeductions> findByIdTypeOperation(Integer idTypeOperation) {
        return cPerceptionsDeductionsDao.findByIdTypeOperation(idTypeOperation);
    }
}
