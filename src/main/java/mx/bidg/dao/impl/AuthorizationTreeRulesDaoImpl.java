package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.AuthorizationTreeRulesDao;
import mx.bidg.model.AuthorizationTreeRules;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
@Repository
public class AuthorizationTreeRulesDaoImpl extends AbstractDao<Integer, AuthorizationTreeRules> implements AuthorizationTreeRulesDao {
    @Override
    public AuthorizationTreeRules findRuleByName(String ruleName) {
        return (AuthorizationTreeRules) createEntityCriteria()
                .add(Restrictions.eq("ruleName", ruleName))
                .uniqueResult();
    }

    @Override
    public AuthorizationTreeRules save(AuthorizationTreeRules entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public AuthorizationTreeRules findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<AuthorizationTreeRules> findAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public AuthorizationTreeRules update(AuthorizationTreeRules entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(AuthorizationTreeRules entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
