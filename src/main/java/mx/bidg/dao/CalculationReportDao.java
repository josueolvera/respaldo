package mx.bidg.dao;

import mx.bidg.model.CalculationReport;

import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
public interface CalculationReportDao extends InterfaceDao<CalculationReport> {
    CalculationReport findByName (String fileName);
    List<CalculationReport> findReportsGeneratedAndNotSended();
    List<CalculationReport> findReportsGeneratedAndSendedNotAuthorized();
    List<CalculationReport> findAllReportsAuthorizedCorporate();
    List<CalculationReport> findAllReportsAuthorizedOutsourcing();
}
