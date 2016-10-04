package mx.bidg.service;

import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.DistributorPerceptionDeduction;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface DistributorPerceptionDeductionService {
    DistributorPerceptionDeduction save(DistributorPerceptionDeduction distributorPerceptionDeduction);
    DistributorPerceptionDeduction update(DistributorPerceptionDeduction distributorPerceptionDeduction);
    DistributorPerceptionDeduction findById(Integer idDistributorPd);
    List<DistributorPerceptionDeduction> findAll();
    boolean delete (DistributorPerceptionDeduction distributorPerceptionDeduction);
    List<DistributorPerceptionDeduction> findPDbyDistributorSelected(Integer idDistributor);
    List<DistributorPerceptionDeduction> findByDistributorAll(Integer idDistributor);
    List<DistributorPerceptionDeduction> saveNewPDtoDistributors (CPerceptionsDeductions cPerceptionsDeductions);
}
