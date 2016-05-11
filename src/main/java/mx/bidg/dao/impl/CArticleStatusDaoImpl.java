package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CArticleStatusDao;
import mx.bidg.model.CArticleStatus;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 25/01/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CArticleStatusDaoImpl extends AbstractDao<Integer, CArticleStatus> implements CArticleStatusDao {
    @Override
    public CArticleStatus save(CArticleStatus entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CArticleStatus findById(int id) {
        return (CArticleStatus) createEntityCriteria()
                .add(Restrictions.eq("idArticleStatus", id))
                .uniqueResult();
    }

    @Override
    public List<CArticleStatus> findAll() {
        return (List<CArticleStatus>) createEntityCriteria()
                .list();
    }

    @Override
    public CArticleStatus update(CArticleStatus entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CArticleStatus entity) {
        remove(entity);
        return true;
    }
}
