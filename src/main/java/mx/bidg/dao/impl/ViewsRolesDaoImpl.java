package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.ViewsRolesDao;
import mx.bidg.model.ViewsRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/11/15.
 */
@Repository
public class ViewsRolesDaoImpl extends AbstractDao<Integer, ViewsRole> implements ViewsRolesDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<ViewsRole> findByRoles(int[] roles) {
        Junction dis = Restrictions.disjunction();
        for (int rol : roles) {
            dis = dis.add(Restrictions.eq("idSystemRole", rol));
        }
        return (List<ViewsRole>) createEntityCriteria().add(dis).list();
    }

    @Override
    public ViewsRole save(ViewsRole entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public ViewsRole findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<ViewsRole> findAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public ViewsRole update(ViewsRole entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(ViewsRole entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
