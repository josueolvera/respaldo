package mx.bidg.service.impl;

import mx.bidg.dao.CalculationReportDao;
import mx.bidg.dao.SqlQueriesDao;
import mx.bidg.model.CalculationReport;
import mx.bidg.model.SqlQueries;
import mx.bidg.service.CalculationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * Created by josue on 02/12/2016.
 */
@Service
@Transactional
public class CalculationReportServiceImpl implements CalculationReportService {

    @Autowired
    CalculationReportDao calculationReportDao;

    @Autowired
    private Environment env;

    @Autowired
    SqlQueriesDao sqlQueriesDao;

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

    @Override
    public CalculationReport findByName(String fileName) {
        return calculationReportDao.findByName(fileName);
    }

    @Override
    public List<CalculationReport> deleteReportsAndRegister(String fileName, String fileNec) {

        String SAVE_PATH = env.getRequiredProperty("report_commission.documents_dir");

        String destinationFile = SAVE_PATH+fileName+".xlsx";
        String destinationFileNec = SAVE_PATH+fileNec+".xlsx";

        File currentFile = new File(destinationFile);
        File currentFileNec = new File(destinationFileNec);

        CalculationReport currentReport = calculationReportDao.findByName(fileName);
        CalculationReport currentReportNec = calculationReportDao.findByName(fileNec);

        if (currentReport != null && currentReportNec != null) {

            SqlQueries sqlQueries = sqlQueriesDao.findById(currentReport.getIdQuery());
            sqlQueries.setCalculate(1);
            sqlQueriesDao.update(sqlQueries);

            calculationReportDao.delete(currentReport);
            calculationReportDao.delete(currentReportNec);

            if (currentFile.exists() && currentFileNec.exists()) {
                currentFile.delete();
                currentFileNec.delete();
            }
        }

        return calculationReportDao.findReportsGeneratedAndNotSended();
    }

    @Override
    public List<CalculationReport> findAllReportsNotSendedAndNotAuthorized() {
        return calculationReportDao.findReportsGeneratedAndNotSended();
    }

    @Override
    public List<CalculationReport> findAllAuthorizedCorporate() {
        return calculationReportDao.findAllReportsAuthorizedCorporate();
    }

    @Override
    public List<CalculationReport> findAllAuthorizedOutsourcing() {
        return calculationReportDao.findAllReportsAuthorizedOutsourcing();
    }
}
