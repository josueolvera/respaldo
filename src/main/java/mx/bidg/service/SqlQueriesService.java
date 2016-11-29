package mx.bidg.service;

import mx.bidg.model.SqlQueries;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 21/06/16.
 */
public interface SqlQueriesService {
    SqlQueries save(SqlQueries query);
    SqlQueries findById(Integer idQuery);
    SqlQueries findByName(String queryName);
    List<SqlQueries> findAll();
    void buildCSVfrom(SqlQueries query, OutputStream stream) throws IOException;
    List<SqlQueries> findByAllWithFlag();
    SqlQueries findQuery(Integer idQuery);
    List executeAPocedureFrom(SqlQueries query, OutputStream stream, String startDate, String endDate, LocalDateTime applicationDate1, LocalDateTime applicationDate2, String week4Init) throws IOException;
}
