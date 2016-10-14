package mx.bidg.service;

import mx.bidg.model.BonusCommisionableEmployee;

import java.util.List;

/**
 * Created by josue on 11/10/2016.
 */
public interface BonusCommisionableEmployeeService {
    BonusCommisionableEmployee save(BonusCommisionableEmployee bonusCommisionableEmployee);
    BonusCommisionableEmployee update (BonusCommisionableEmployee bonusCommisionableEmployee);
    BonusCommisionableEmployee findById(Integer idBonusCommisionableEmployee);
    List<BonusCommisionableEmployee> findAll();
    boolean delete(BonusCommisionableEmployee bonusCommisionableEmployee);
}
