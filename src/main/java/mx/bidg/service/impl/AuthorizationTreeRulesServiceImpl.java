package mx.bidg.service.impl;

import mx.bidg.dao.AuthorizationTreeRulesDao;
import mx.bidg.model.AuthorizationTreeRules;
import mx.bidg.service.AuthorizationTreeRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
@Service
@Transactional
public class AuthorizationTreeRulesServiceImpl implements AuthorizationTreeRulesService {

    @Autowired
    private AuthorizationTreeRulesDao authorizationTreeRulesDao;

    @Override
    public AuthorizationTreeRules findByRuleName(String ruleName) {
        return authorizationTreeRulesDao.findRuleByName(ruleName);
    }
}
