package mx.bidg.service.impl;

import mx.bidg.dao.StockEmployeeAssignmentsDao;
import mx.bidg.model.StockEmployeeAssignments;
import mx.bidg.model.Stocks;
import mx.bidg.service.StockEmployeeAssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 19/01/16.
 */
@Service
@Transactional
public class StockEmployeeAssignmentsServiceImpl implements StockEmployeeAssignmentsService {

    @Autowired
    private StockEmployeeAssignmentsDao employeeAssignmentsDao;

    @Override
    public List<StockEmployeeAssignments> getAssignmentsFor(Stocks stock) {
        return employeeAssignmentsDao.findFor(stock);
    }

    @Override
    public List<StockEmployeeAssignments> getAssignmentsRecordFor(Stocks stock) {
        return employeeAssignmentsDao.findRecordFor(stock);
    }

    @Override
    public boolean saveAssignment(StockEmployeeAssignments assignment) {
        return employeeAssignmentsDao.delete(assignment);
    }
}
