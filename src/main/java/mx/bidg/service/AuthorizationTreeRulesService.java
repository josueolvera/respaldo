package mx.bidg.service;

import mx.bidg.model.AuthorizationTreeRules;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
public interface AuthorizationTreeRulesService {
    AuthorizationTreeRules findByRuleName(String ruleName);

}
