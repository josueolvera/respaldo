package mx.bidg.dao.impl;

import java.util.List;
import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CRoomsDao;
import mx.bidg.model.CRooms;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CRoomsDaoImpl extends AbstractDao<Integer, CRooms> implements CRoomsDao {

    @Override
    public CRooms save(CRooms entity) {
        return save(entity);
    }

    @Override
    public CRooms findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CRooms> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<CRooms>) criteria.list();
    }

    @Override
    public CRooms update(CRooms entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CRooms entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CRooms> getRooms(Integer idRole) {
         Criteria criteria = createEntityCriteria();

        if (idRole != null) {
            criteria.createAlias("roles", "r");
            criteria.add(Restrictions.eq("r.idRole", idRole));
        }

        return criteria.list();
    }
    
}
