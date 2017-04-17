package mx.bidg.service;

import mx.bidg.model.CCostCenterStatus;

import java.util.List;

/**
 * Created by Desarrollador on 05/04/2017.
 */
public interface CCostCenterStatusService {
    CCostCenterStatus save(CCostCenterStatus cCostCenterStatus);
    CCostCenterStatus update(CCostCenterStatus cCostCenterStatus);
    CCostCenterStatus findById(Integer idCCostCenterStatus);
    List<CCostCenterStatus> findAll();
    boolean delete(CCostCenterStatus cCostCenterStatus);
}
