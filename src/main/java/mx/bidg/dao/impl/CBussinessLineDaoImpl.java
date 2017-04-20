package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CBussinessLineDao;
import mx.bidg.model.CBussinessLine;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin Salvador on 21/03/2017.
 */
@Repository
public class CBussinessLineDaoImpl extends AbstractDao<Integer, CBussinessLine> implements CBussinessLineDao{

    @Override
    public CBussinessLine save(CBussinessLine entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CBussinessLine findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CBussinessLine> findAll() {
        return createEntityCriteria().add(Restrictions.ne("idBusinessLine",0)).list();
    }

    @Override
    public CBussinessLine update(CBussinessLine entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CBussinessLine entity) {
        remove(entity);
        return true;
    }
}
