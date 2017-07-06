package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorsDetailBanksHistoryDao;
import mx.bidg.model.DistributorsDetailBanksHistory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Leonardo on 04/07/2017.
 */
@SuppressWarnings("unchecked")
@Repository
public class DistributorsDetailBanksHistoryDaoImpl extends AbstractDao<Integer, DistributorsDetailBanksHistory> implements DistributorsDetailBanksHistoryDao {
    @Override
    public DistributorsDetailBanksHistory save (DistributorsDetailBanksHistory entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DistributorsDetailBanksHistory findById(int id) {return getByKey(id);}

    @Override
    public List<DistributorsDetailBanksHistory> findAll() {
        return createEntityCriteria()
                .list();
    }

    @Override
    public DistributorsDetailBanksHistory update(DistributorsDetailBanksHistory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DistributorsDetailBanksHistory entity) {
        remove(entity);
        return true;
    }
}
