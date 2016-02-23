package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.NotificationsDao;
import mx.bidg.model.CNotificationsStatus;
import mx.bidg.model.Notifications;
import mx.bidg.model.Users;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 10/02/16.
 */
@Repository
public class NotificationsDaoImpl extends AbstractDao<Integer, Notifications> implements NotificationsDao {
    @Override
    public Notifications save(Notifications entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Notifications findById(int id) {
        return (Notifications) createEntityCriteria()
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Notifications> findAll() {
        return (List<Notifications>) createEntityCriteria().list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Notifications> findAllForUser(Users user) {
        return (List<Notifications>) createEntityCriteria()
                .add(Restrictions.eq("idUser", user.getIdUser()))
                .add(Restrictions.disjunction(
                        Restrictions.eq("idNotificationStatus", CNotificationsStatus.PENDIENTE),
                        Restrictions.eq("idNotificationStatus", CNotificationsStatus.LEIDA)
                ))
                .addOrder(Order.asc("dueDate"))
                .list();
    }

    @Override
    public Long countNotificationsForUser(Users user) {
        return (Long) getSession()
                .createQuery(
                        "select count(n) from Notifications n where n.idUser = :idUser " +
                        "and (n.idNotificationStatus = :idPendiente or n.idNotificationStatus = :idLeida)"
                )
                .setInteger("idUser", user.getIdUser())
                .setInteger("idPendiente", CNotificationsStatus.PENDIENTE)
                .setInteger("idLeida", CNotificationsStatus.LEIDA)
                .uniqueResult();
    }

    @Override
    public Notifications update(Notifications entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Notifications entity) {
        remove(entity);
        return true;
    }
}
