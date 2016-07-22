package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CContractTypeDao;
import mx.bidg.model.CContractType;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
@Repository
public class CContractTypeDaoImpl extends AbstractDao<Integer, CContractType> implements CContractTypeDao {

    @Override
    public CContractType save(CContractType entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CContractType findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CContractType> findAll() {
        return (List<CContractType>) createEntityCriteria().list();
    }

    @Override
    public CContractType update(CContractType entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CContractType entity) {
        remove(entity);
        return true;
    }

    @Override
    public CContractType findByContractTypeName(String contractTypeName) {
        return (CContractType) createEntityCriteria().add(Restrictions.eq("contractTypeName", contractTypeName)).uniqueResult();
    }
}
