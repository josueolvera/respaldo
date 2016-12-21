package mx.bidg.dao;

import mx.bidg.model.DwBranchs;
import mx.bidg.model.DwEnterprises;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
public interface DwBranchsDao extends InterfaceDao<DwBranchs> {
    BigDecimal sumGoalByZoneOrRegion (List<DwEnterprises> branchsList);
}
