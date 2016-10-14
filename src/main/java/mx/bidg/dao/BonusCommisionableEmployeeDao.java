package mx.bidg.dao;

import mx.bidg.model.BonusCommisionableEmployee;

import java.util.List;

/**
 * Created by josue on 11/10/2016.
 */
public interface BonusCommisionableEmployeeDao extends InterfaceDao<BonusCommisionableEmployee> {
    List<BonusCommisionableEmployee> findByIdEmployee(Integer idEmployee);
}
