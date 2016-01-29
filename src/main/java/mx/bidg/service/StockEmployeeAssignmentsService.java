package mx.bidg.service;

import mx.bidg.model.StockEmployeeAssignments;
import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 19/01/16.
 */
public interface StockEmployeeAssignmentsService {
    StockEmployeeAssignments getAssignmentFor(Stocks stock);
    List<StockEmployeeAssignments> getAssignmentsRecordFor(Stocks stock);
    boolean saveAssignment(StockEmployeeAssignments assignment);
    StockEmployeeAssignments update(StockEmployeeAssignments assignment);
}
