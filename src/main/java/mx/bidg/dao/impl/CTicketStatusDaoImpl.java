package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CTicketStatusDao;
import mx.bidg.model.CTicketStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class CTicketStatusDaoImpl extends AbstractDao<Integer,CTicketStatus> implements CTicketStatusDao {
    @Override
    public CTicketStatus save(CTicketStatus entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CTicketStatus findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CTicketStatus> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CTicketStatus update(CTicketStatus entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CTicketStatus entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
