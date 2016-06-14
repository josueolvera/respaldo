package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.TicketDao;
import mx.bidg.model.CTicketsCategories;
import mx.bidg.model.Ticket;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class TicketDaoImpl extends AbstractDao<Integer,Ticket> implements TicketDao {
    @Override
    public Ticket save(Ticket entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Ticket findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<Ticket> findAll() {
        return createEntityCriteria()
                .addOrder( Order.asc("ticketStatus.idTicketStatus") )
                .list();
    }

    @Override
    public List<Ticket> findAll(CTicketsCategories category) {
        return createEntityCriteria()
                .addOrder( Order.asc("ticketStatus.idTicketStatus"))
                .createCriteria("incidence", JoinType.INNER_JOIN)
                    .add(Restrictions.eq("idTicketCategory", category.getIdTicketCategory()))
                .list();
    }

    @Override
    public Ticket update(Ticket entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Ticket entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<Ticket> findByPriority(Integer idPriority) {
        return createEntityCriteria()
                .add(Restrictions.eq("priority.idPriority",idPriority))
                .list();
    }

    @Override
    public List<Ticket> findByTicketStatus(Integer idTicketStatus) {
        return createEntityCriteria()
                .add(Restrictions.eq("ticketStatus.idTicketStatus",idTicketStatus))
                .list();
    }

    @Override
    public List<Ticket> findByTicketStatusPriority(Integer idTicketStatus, Integer idPriority) {
        return createEntityCriteria()
                .add(Restrictions.eq("idPriority",idPriority))
                .add(Restrictions.eq("idTicketStatus",idTicketStatus))
                .list();
    }

    @Override
    public Ticket findByFolio(String folio) {
        return (Ticket) createEntityCriteria()
                .add(Restrictions.eq("folio",folio.trim()))
                .uniqueResult();
    }
}
