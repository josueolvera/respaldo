package mx.bidg.dao;

import mx.bidg.model.AuthorizationTreeRules;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
public interface AuthorizationTreeRulesDao extends InterfaceDao<AuthorizationTreeRules> {
    AuthorizationTreeRules findRuleByName(String ruleName);
}
