package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RequestBudgetSpendingDao;
import mx.bidg.model.RequestBudgetSpending;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by desarrollador on 19/06/17.
 */
@Repository
public class RequestBudgetSpendingDaoImpl extends AbstractDao<Integer, RequestBudgetSpending> implements RequestBudgetSpendingDao {

    @Override
    public RequestBudgetSpending save(RequestBudgetSpending entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RequestBudgetSpending findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RequestBudgetSpending> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public RequestBudgetSpending update(RequestBudgetSpending entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RequestBudgetSpending entity) {
        remove(entity);
        return true;
    }

    @Override
    public RequestBudgetSpending findByIdDistributorCostCenterYear(Integer idDistributorCostCenter, int year) {
        return (RequestBudgetSpending) createEntityCriteria()
                .add(Restrictions.eq("idDistributorCostCenter", idDistributorCostCenter))
                .add(Restrictions.eq("year", year))
                .uniqueResult();
    }

    @Override
    public BigDecimal getAmountDistributorCostCenter(Integer idDistributorCostCenter, int month, int year) {
        Criteria criteria = createEntityCriteria();

        switch (month){
            case 1:
                criteria.setProjection(Projections.sum("januaryAmount"));
                break;
            case 2:
                criteria.setProjection(Projections.sum("februaryAmount"));
                break;
            case 3:
                criteria.setProjection(Projections.sum("marchAmount"));
                break;
            case 4:
                criteria.setProjection(Projections.sum("aprilAmount"));
                break;
            case 5:
                criteria.setProjection(Projections.sum("mayAmount"));
                break;
            case 6:
                criteria.setProjection(Projections.sum("juneAmount"));
                break;
            case 7:
                criteria.setProjection(Projections.sum("julyAmount"));
                break;
            case 8:
                criteria.setProjection(Projections.sum("augustAmount"));
                break;
            case 9:
                criteria.setProjection(Projections.sum("septemberAmount"));
                break;
            case 10:
                criteria.setProjection(Projections.sum("octoberAmount"));
                break;
            case 11:
                criteria.setProjection(Projections.sum("novemberAmount"));
                break;
            case 12:
                criteria.setProjection(Projections.sum("decemberAmount"));
                break;

        }
        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributorCostCenter", idDistributorCostCenter))
                .add(Restrictions.eq("year", year))
                .uniqueResult();
    }

    @Override
    public BigDecimal getAmountExpendedDistributorCostCenter(Integer idDistributorCostCenter, int month, int year) {
        Criteria criteria = createEntityCriteria();

        switch (month){
            case 1:
                criteria.setProjection(Projections.sum("januarySpended"));
                break;
            case 2:
                criteria.setProjection(Projections.sum("februarySpended"));
                break;
            case 3:
                criteria.setProjection(Projections.sum("marchSpended"));
                break;
            case 4:
                criteria.setProjection(Projections.sum("aprilSpended"));
                break;
            case 5:
                criteria.setProjection(Projections.sum("maySpended"));
                break;
            case 6:
                criteria.setProjection(Projections.sum("juneSpended"));
                break;
            case 7:
                criteria.setProjection(Projections.sum("julySpended"));
                break;
            case 8:
                criteria.setProjection(Projections.sum("augustSpended"));
                break;
            case 9:
                criteria.setProjection(Projections.sum("septemberSpended"));
                break;
            case 10:
                criteria.setProjection(Projections.sum("octoberSpended"));
                break;
            case 11:
                criteria.setProjection(Projections.sum("novemberSpended"));
                break;
            case 12:
                criteria.setProjection(Projections.sum("decemberSpended"));
                break;

        }
        return (BigDecimal) criteria
                .add(Restrictions.eq("idDistributorCostCenter", idDistributorCostCenter))
                .add(Restrictions.eq("year", year))
                .uniqueResult();
    }
}
