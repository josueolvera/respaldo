package mx.bidg.dao;

import mx.bidg.model.StockEmployeeAssignments;
import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 19/01/16.
 */
public interface StockEmployeeAssignmentsDao extends InterfaceDao<StockEmployeeAssignments> {
    StockEmployeeAssignments findFor(Stocks stock);
    List<StockEmployeeAssignments> findRecordFor(Stocks stock);
}
