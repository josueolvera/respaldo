package mx.bidg.service.impl;

import mx.bidg.dao.CDistributorsDao;
import mx.bidg.dao.DistributorPerceptionDeductionDao;
import mx.bidg.model.CDistributors;
import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.DistributorPerceptionDeduction;
import mx.bidg.service.DistributorPerceptionDeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Service
@Transactional
public class DistributorPerceptionDeductionServiceImpl implements DistributorPerceptionDeductionService {

    @Autowired
    DistributorPerceptionDeductionDao distributorPerceptionDeductionDao;

    @Autowired
    CDistributorsDao cDistributorsDao;

    @Override
    public DistributorPerceptionDeduction save(DistributorPerceptionDeduction distributorPerceptionDeduction) {
        return distributorPerceptionDeductionDao.save(distributorPerceptionDeduction);
    }

    @Override
    public DistributorPerceptionDeduction update(DistributorPerceptionDeduction distributorPerceptionDeduction) {
        return distributorPerceptionDeductionDao.update(distributorPerceptionDeduction);
    }

    @Override
    public DistributorPerceptionDeduction findById(Integer idDistributorPd) {
        return distributorPerceptionDeductionDao.findById(idDistributorPd);
    }

    @Override
    public List<DistributorPerceptionDeduction> findAll() {
        return distributorPerceptionDeductionDao.findAll();
    }

    @Override
    public boolean delete(DistributorPerceptionDeduction distributorPerceptionDeduction) {
        distributorPerceptionDeductionDao.delete(distributorPerceptionDeduction);
        return true;
    }

    @Override
    public List<DistributorPerceptionDeduction> findPDbyDistributorSelected(Integer idDistributor) {
        return distributorPerceptionDeductionDao.findByDistributorAndHpd(idDistributor);
    }

    @Override
    public List<DistributorPerceptionDeduction> findByDistributorAll(Integer idDistributor) {
        return distributorPerceptionDeductionDao.findByDistributorAll(idDistributor);
    }

    @Override
    public List<DistributorPerceptionDeduction> saveNewPDtoDistributors(CPerceptionsDeductions cPerceptionsDeductions) {

        List<CDistributors> distributorsList = cDistributorsDao.findAll();

        for (CDistributors distributors: distributorsList){
            DistributorPerceptionDeduction distributorPerceptionDeduction = new DistributorPerceptionDeduction();
            distributorPerceptionDeduction.setDistributor(distributors);
            distributorPerceptionDeduction.setcPerceptionsDeductions(cPerceptionsDeductions);
            distributorPerceptionDeduction.setHasPd(false);

            distributorPerceptionDeductionDao.save(distributorPerceptionDeduction);
        }
        return distributorPerceptionDeductionDao.findAll();
    }
}
