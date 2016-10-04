package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.GroupApportionmentDao;
import mx.bidg.model.GroupApportionment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Repository
public class GroupApportionmentDaoImpl extends AbstractDao<Integer, GroupApportionment> implements GroupApportionmentDao {

    @Override
    public GroupApportionment save(GroupApportionment entity) {
        persist(entity);
        return entity;
    }

    @Override
    public GroupApportionment findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<GroupApportionment> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public GroupApportionment update(GroupApportionment entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(GroupApportionment entity) {
        remove(entity);
        return true;
    }
}
