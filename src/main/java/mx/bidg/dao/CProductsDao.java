package mx.bidg.dao;

import mx.bidg.model.CProducts;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
public interface CProductsDao extends InterfaceDao<CProducts> {
    List<CProducts> findByBudgetSubcategory(int idBudgetSubcategory);
}
