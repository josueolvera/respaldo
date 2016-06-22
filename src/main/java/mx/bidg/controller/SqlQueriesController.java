package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CDataTypes;
import mx.bidg.model.SqlQueries;
import mx.bidg.model.SqlQueryParameters;
import mx.bidg.service.SqlQueriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rafael Viveros
 * Created on 21/06/16.
 */
@Controller
@RequestMapping("sql-queries")
public class SqlQueriesController {

    @Autowired
    private SqlQueriesService sqlQueriesService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<SqlQueries> sqlQueries = sqlQueriesService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sqlQueries),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        SqlQueries query = new SqlQueries();
        query.setQueryName(node.get("queryName").asText());
        query.setSqlQuery(node.get("sqlQuery").asText());
        query.setHeaders(node.get("headers").asText());

        Set<SqlQueryParameters> queryParameters = new HashSet<>();
        for (JsonNode queryNode : node.get("queryParameters")) {
            SqlQueryParameters param = new SqlQueryParameters();
            param.setDataTypes(CDataTypes.VARCHAR);
            param.setParameterName(queryNode.get("parameterName").asText());
            param.setParameterValue(queryNode.get("parameterValue").asText());
            queryParameters.add(param);
        }
        query.setQueryParameters(queryParameters);

        sqlQueriesService.save(query);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{idSqlQuery}/build", method = RequestMethod.GET)
    public void buildSqlQueryCSV(
            @PathVariable int idSqlQuery, @RequestParam(name = "file_name", required = true) String fileName,
            HttpServletResponse response
    ) throws Exception {
        OutputStream outputStream = response.getOutputStream();
        SqlQueries query = sqlQueriesService.findById(idSqlQuery);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".csv\"");

        sqlQueriesService.buildCSVfrom(query, outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
