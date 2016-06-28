package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.model.EmployeesHistory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class EmployeesHistoryDaoImpl extends AbstractDao<Integer, EmployeesHistory> implements EmployeesHistoryDao {
    @Override
    public EmployeesHistory save(EmployeesHistory entity) {
        persist(entity);
        return entity;
    }

    @Override
    public EmployeesHistory findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<EmployeesHistory> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public EmployeesHistory update(EmployeesHistory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(EmployeesHistory entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRole(Integer idDistributor, Integer idRegion, Integer idBranch, Integer idArea, Integer idRole, String startDate, String endDate) {
        Criteria criteria = createEntityCriteria();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        boolean hasRestrictions = false;

        if (startDate != null && endDate != null) {
            LocalDateTime startLocalDateTime = LocalDateTime.parse(startDate + " 00:00:00",formatter);
            LocalDateTime endLocalDateTime = LocalDateTime.parse(endDate + " 00:00:00",formatter);
            criteria.add(Restrictions.between("joinDate",startLocalDateTime,endLocalDateTime));
            hasRestrictions = true;
        }

        if (idDistributor != null) {
            criteria.add(Restrictions.eq("idDistributor",idDistributor));
            hasRestrictions = true;
        }
        if (idRegion != null) {
            criteria.add(Restrictions.eq("idRegion",idRegion));
            hasRestrictions = true;
        }
        if (idBranch != null) {
            criteria.add(Restrictions.eq("idBranch",idBranch));
            hasRestrictions = true;
        }
        if (idArea != null) {
            criteria.add(Restrictions.eq("idArea",idArea));
            hasRestrictions = true;
        }
        if (idRole != null) {
            criteria.add(Restrictions.eq("idRole",idRole));
            hasRestrictions = true;
        }

        if (!hasRestrictions) {
            return null;
        }

        return criteria.list();
    }
}
