/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import mx.bidg.dao.CBudgetConceptsDao;
import mx.bidg.model.CBudgetConcepts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CBudgetConceptsServiceImpl implements CBudgetConceptsService {
    
    @Autowired
    CBudgetConceptsDao budgetConceptsDao;

    @Override
    public CBudgetConcepts save(CBudgetConcepts cBudgetConcept) {
        return budgetConceptsDao.save(cBudgetConcept);
    }
    
}
