package mx.bidg.service;

import mx.bidg.model.CCostCenter;

import java.util.List;

/**
 * Created by gerardo8 on 09/09/16.
 */
public interface CCostCenterService {
    List<CCostCenter> findAll();
    CCostCenter findById(Integer idCostCenter);
}
