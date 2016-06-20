package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DwEnterprisesAgreementsDao;
import mx.bidg.model.DwEnterprisesAgreements;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
@Repository
public class DwEnterprisesAgreementsDaoImpl extends AbstractDao<Integer,DwEnterprisesAgreements> implements DwEnterprisesAgreementsDao {

    @Override
    public DwEnterprisesAgreements save(DwEnterprisesAgreements entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DwEnterprisesAgreements findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DwEnterprisesAgreements> findAll() {
        return (List<DwEnterprisesAgreements>) createEntityCriteria().list();
    }

    @Override
    public DwEnterprisesAgreements update(DwEnterprisesAgreements entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DwEnterprisesAgreements entity) {
        remove(entity);
        return true;
    }
}
