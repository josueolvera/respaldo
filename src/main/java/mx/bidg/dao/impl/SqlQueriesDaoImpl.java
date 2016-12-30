package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.SqlQueriesDao;
import mx.bidg.model.CDataTypes;
import mx.bidg.model.SqlQueries;
import mx.bidg.model.SqlQueryParameters;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Rafael Viveros
 * Created on 20/06/16.
 */
@Repository
public class SqlQueriesDaoImpl extends AbstractDao<Integer, SqlQueries> implements SqlQueriesDao {
    @Override
    public SqlQueries save(SqlQueries entity) {
        persist(entity);
        return entity;
    }

    @Override
    public SqlQueries findById(int id) {
        return (SqlQueries) createEntityCriteria()
                .setFetchMode("queryParameters", FetchMode.JOIN)
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<SqlQueries> findAll() {
        return createEntityCriteria()
                .list();
    }

    @Override
    public SqlQueries update(SqlQueries entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(SqlQueries entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public SqlQueries findByName(String queryName) {
        return (SqlQueries) createEntityCriteria()
                .setFetchMode("queryParameters", FetchMode.JOIN)
                .add(Restrictions.eq("queryName", queryName))
                .uniqueResult();
    }

    @Override
    public List<Map<String, Object>> executeQuery(SqlQueries query) {
        SQLQuery sqlQuery = getSession().createSQLQuery(query.getSqlQuery());
        for (SqlQueryParameters parameter : query.getQueryParameters()) {
            switch (parameter.getIdDataType()) {
                case CDataTypes.ID_INT:
                    sqlQuery.setInteger(parameter.getParameterName(), Integer.parseInt(parameter.getParameterValue()));
                    break;
                case CDataTypes.ID_TIMESTAMP:
                    sqlQuery.setTimestamp(parameter.getParameterName(), Date.valueOf(parameter.getParameterValue()));
                    break;
                case CDataTypes.ID_VARCHAR:
                    sqlQuery.setString(parameter.getParameterName(), parameter.getParameterValue());
                    break;
                default:
                    sqlQuery.setString(parameter.getParameterName(), parameter.getParameterValue());
                    break;
            }
        }
        sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return sqlQuery.list();
    }

    @Override
    public List<SqlQueries> findAllQueriesByCalculate() {
        return (List<SqlQueries>) createEntityCriteria().add(Restrictions.eq("calculate",1)).list();
    }

    @Override
    public SqlQueries findQuery(Integer idQuery) {
        return (SqlQueries) getByKey(idQuery);
    }

    @Override
    public List executeProcedurestoReport(SqlQueries query, String startDate, String endDate, String week4Init) {
        SQLQuery sqlQuery = (SQLQuery) getSession().createSQLQuery(query.getSqlQuery())
                .setParameter("fecha1",startDate)
                .setParameter("fecha2",endDate)
                .setParameter("fechaSemanal",week4Init);
        return sqlQuery.list();
    }

    @Override
    public List<SqlQueries> findByCalculateReport() {
        return (List<SqlQueries>) createEntityCriteria().add(Restrictions.eq("calculate",2)).list();
    }
}
