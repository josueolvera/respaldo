package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CSqlDictionaryDao;
import mx.bidg.model.CSqlDictionary;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class CSqlDictionaryDaoImpl extends AbstractDao<Integer, CSqlDictionary> implements CSqlDictionaryDao {

    @Override
    public CSqlDictionary save(CSqlDictionary entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CSqlDictionary findById(int id) {
        return (CSqlDictionary) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public List<CSqlDictionary> findAll() {
        return createEntityCriteria()
                .list();
    }

    @Override
    public CSqlDictionary update(CSqlDictionary entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CSqlDictionary entity) {
        remove(entity);
        return true;
    }
    
}
