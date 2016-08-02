package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.model.EmployeesHistory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Disjunction;

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
        return createEntityCriteria()
                .add(Restrictions.eq("hStatus",1))
                .list();
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
    public List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
        (Integer status, Integer idDistributor, Integer idRegion, Integer idZona,Integer idBranch, Integer idArea, Integer idRole, 
         String startDate, String endDate) {
        Criteria criteria = createEntityCriteria();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        if (startDate != null && endDate != null) {
            LocalDateTime startLocalDateTime = LocalDateTime.parse(startDate + " 00:00:00",formatter);
            LocalDateTime endLocalDateTime = LocalDateTime.parse(endDate + " 23:59:59",formatter);
            criteria.add(Restrictions.between("joinDate",startLocalDateTime,endLocalDateTime));
        }

        if (idDistributor != null) {
            criteria.add(Restrictions.eq("idDistributor",idDistributor));
        }
        if (idRegion != null) {
            criteria.add(Restrictions.eq("idRegion",idRegion));
        }
        if (idZona != null) {
            criteria.add(Restrictions.eq("idZona",idZona));
        }
        if (idBranch != null) {
            criteria.add(Restrictions.eq("idBranch",idBranch));
        }
        if (idArea != null) {
            criteria.add(Restrictions.eq("idArea",idArea));
        }
        if (idRole != null) {
            criteria.add(Restrictions.eq("idRole",idRole));
        }

        if (status == 0) {
           criteria.add(Restrictions.eq("hStatus",1)); 
        }
        else if (status == 1){
            Disjunction or = Restrictions.disjunction();
            criteria.add(Restrictions.eq("hStatus",1));
            criteria.add(Restrictions.disjunction()
                    .add(Restrictions.eq("idActionType",1))
                    .add(Restrictions.eq("idActionType",3))
                    .add(Restrictions.eq("idActionType",4)));
        }
        else{
            criteria.add(Restrictions.eq("hStatus",1));
            criteria.add(Restrictions.eq("idActionType",2));
        }
        

        return criteria.list();
    }

    @Override
    public List<EmployeesHistory> findByIdEmployee(Integer idEmployee) {
        return createEntityCriteria()
                .add(Restrictions.eq("idEmployee",idEmployee))
                .list();
    }
}
