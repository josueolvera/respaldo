package mx.bidg.dao;

import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersBudgetSubcategories;

import java.util.List;
import mx.bidg.model.CBudgetSubcategories;

/**
 * Created by jolvera on 30/05/16.
 */
public interface ProvidersBudgetSubcategoriesDao extends InterfaceDao<ProvidersBudgetSubcategories> {

    List<ProvidersBudgetSubcategories> findByProvider (Providers p);
    List<ProvidersBudgetSubcategories> findByBudgetSubcategorie (CBudgetSubcategories budgetSubcategories);
}
