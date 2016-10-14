package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.BonusCommisionableEmployeeDao;
import mx.bidg.model.BonusCommisionableEmployee;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josue on 11/10/2016.
 */
@Repository
public class BonusCommisionableEmployeeDaoImpl extends AbstractDao<Integer,BonusCommisionableEmployee> implements BonusCommisionableEmployeeDao {

    @Override
    public BonusCommisionableEmployee save(BonusCommisionableEmployee entity) {
        persist(entity);
        return entity;
    }

    @Override
    public BonusCommisionableEmployee findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<BonusCommisionableEmployee> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public BonusCommisionableEmployee update(BonusCommisionableEmployee entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(BonusCommisionableEmployee entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<BonusCommisionableEmployee> findByIdEmployee(Integer idEmployee) {
        return createEntityCriteria()
                .add(Restrictions.eq("idEmployee", idEmployee))
                .list();
    }
}
