package mx.bidg.dao;

import mx.bidg.model.SqlQueries;

import java.util.List;
import java.util.Map;

/**
 * @author Rafael Viveros
 * Created on 20/06/16.
 */
public interface SqlQueriesDao extends InterfaceDao<SqlQueries> {
    SqlQueries findByName(String queryName);
    List<Map<String, Object>> executeQuery(SqlQueries query);
    List<SqlQueries> findAllQueriesByCalculate();
    SqlQueries findQuery (Integer idQuery);
    List executeProcedurestoReport(SqlQueries query, String startDate, String endDate, String week4Init);
    List<SqlQueries>findByCalculateReport();
    List executeReportPerceptionDeduction(SqlQueries query, Integer idEmployee,Integer idDistributor, String startDate, String endDate);
    List lowDWEmployeeUser(SqlQueries query, Integer idUser);
}
