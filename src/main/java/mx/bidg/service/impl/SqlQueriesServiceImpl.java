package mx.bidg.service.impl;

import mx.bidg.dao.SqlQueriesDao;
import mx.bidg.model.SqlQueries;
import mx.bidg.service.PayrollService;
import mx.bidg.service.SqlQueriesService;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Rafael Viveros
 * Created on 21/06/16.
 */
@Service
@Transactional
public class SqlQueriesServiceImpl implements SqlQueriesService {

    @Autowired
    private SqlQueriesDao sqlQueriesDao;

    @Autowired
    private PayrollService payrollService;

    @Override
    public SqlQueries save(SqlQueries query) {
        return sqlQueriesDao.save(query);
    }

    @Override
    public SqlQueries findById(Integer idQuery) {
        return sqlQueriesDao.findById(idQuery);
    }

    @Override
    public List<SqlQueries> findAll() {
        return sqlQueriesDao.findAll();
    }

    @Override
    public SqlQueries findByName(String queryName) {
        return sqlQueriesDao.findByName(queryName);
    }

    @Override
    public void buildCSVfrom(SqlQueries query, OutputStream outputStream) throws IOException {
        ICsvMapWriter csvMapWriter = new CsvMapWriter(new OutputStreamWriter(outputStream), CsvPreference.EXCEL_PREFERENCE, true);
        List<Map<String, Object>> queryResult = sqlQueriesDao.executeQuery(query);
        String[] headers = query.getHeaders().split(",");

        csvMapWriter.writeHeader(headers);
        for (Map<String, Object> row : queryResult) {
            csvMapWriter.write(row, headers);
        }
        csvMapWriter.flush();
        csvMapWriter.close();
    }

    @Override
    public List<SqlQueries> findByAllWithFlag() {
        return sqlQueriesDao.findAllQueriesByCalculate();
    }

    @Override
    public SqlQueries findQuery(Integer idQuery) {
        return sqlQueriesDao.findQuery(idQuery);
    }

    @Override
    public List executeAPocedureFrom(SqlQueries query, OutputStream stream, String startDate, String endDate, LocalDateTime applicationDate1, LocalDateTime applicationDate2, String week4Init, FileOutputStream fileOutputStream, FileOutputStream fileOutputStreamNec) throws IOException {
        List queryResult = sqlQueriesDao.executeProcedurestoReport(query, startDate, endDate, week4Init);
        if (query.getIdQuery() == 1){
            payrollService.reportCorporate(stream, applicationDate1, applicationDate2, fileOutputStream);
            payrollService.reportCorporateNec(fileOutputStreamNec);
        }else if (query.getIdQuery() == 2){
            payrollService.corporateFortyName(stream, applicationDate1, applicationDate2, fileOutputStream);
            payrollService.reportDistributionNec(fileOutputStreamNec);
        }else  if (query.getIdQuery() == 4){
            payrollService.reportWeeklyPay(stream, applicationDate1, applicationDate2, fileOutputStream);
            payrollService.reportDistributionNec(fileOutputStreamNec);
        }else  if (query.getIdQuery() == 5){
            payrollService.monthlyPayrollReport(stream, applicationDate1, applicationDate2, fileOutputStream);
            payrollService.reportDistributionNec(fileOutputStreamNec);
        }

        return queryResult;
    }

    @Override
    public SqlQueries update(SqlQueries query) {
        return sqlQueriesDao.update( query);
    }

    @Override
    public List<SqlQueries> findByReportCost() {
        return sqlQueriesDao.findByCalculateReport();
    }

    @Override
    public List executeProcedureReportCost(SqlQueries query, String startDate, String endDate, String week4Init) {
        List queryResult = sqlQueriesDao.executeProcedurestoReport(query,startDate,endDate,week4Init);
        return queryResult;
    }

}
