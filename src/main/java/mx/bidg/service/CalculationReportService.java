package mx.bidg.service;

import mx.bidg.model.CalculationReport;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
public interface CalculationReportService {

    CalculationReport save (CalculationReport calculationReport);
    CalculationReport update(CalculationReport calculationReport);
    CalculationReport findById (Integer idCalculationReport);
    List<CalculationReport> findAll();
    boolean delete(CalculationReport calculationReport);
    CalculationReport findByName(String fileName);
    List<CalculationReport> deleteReportsAndRegister(String fileName, String fileNameNec);
    List<CalculationReport> findAllReportsNotSendedAndNotAuthorized();
    List<CalculationReport> findAllAuthorizedCorporate();
    List<CalculationReport> findAllAuthorizedOutsourcing();
}
