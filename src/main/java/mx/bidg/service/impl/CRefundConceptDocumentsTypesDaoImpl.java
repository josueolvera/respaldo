package mx.bidg.service.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRefundConceptDocumentsTypesDao;
import mx.bidg.model.CRefundConceptDocumentsTypes;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 22/07/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CRefundConceptDocumentsTypesDaoImpl extends AbstractDao<Integer,CRefundConceptDocumentsTypes> implements CRefundConceptDocumentsTypesDao {

    @Override
    public CRefundConceptDocumentsTypes save(CRefundConceptDocumentsTypes entity) {
        persist(entity);
        return entity;
    }

    @Override
    public CRefundConceptDocumentsTypes findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRefundConceptDocumentsTypes> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CRefundConceptDocumentsTypes update(CRefundConceptDocumentsTypes entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(CRefundConceptDocumentsTypes entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<CRefundConceptDocumentsTypes> findByIdVoucherType(Integer idVoucherType) {
        return createEntityCriteria()
                .add(Restrictions.eq("idVoucherType",idVoucherType))
                .list();
    }
}
