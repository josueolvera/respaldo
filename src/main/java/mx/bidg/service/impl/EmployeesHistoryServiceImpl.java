package mx.bidg.service.impl;

import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.service.EmployeesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gerardo8 on 24/06/16.
 */
@Service
@Transactional
public class EmployeesHistoryServiceImpl implements EmployeesHistoryService {

    @Autowired
    private EmployeesHistoryDao employeesHistoryDao;
}
