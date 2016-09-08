package mx.bidg.service;

import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersBudgetSubcategories;

import java.util.List;
import mx.bidg.model.CBudgetSubcategories;

/**
 * Created by jolvera on 30/05/16.
 */
public interface ProvidersBudgetSubcategoriesService {

    ProvidersBudgetSubcategories save(ProvidersBudgetSubcategories providersBudgetSubcategories);
    ProvidersBudgetSubcategories update(ProvidersBudgetSubcategories providersBudgetSubcategories);
    boolean delete(ProvidersBudgetSubcategories providersBudgetSubcategories);
    List<ProvidersBudgetSubcategories> findAll();
    ProvidersBudgetSubcategories findById(int idProvidersProductsTypes);
    List<ProvidersBudgetSubcategories> findByProvider(Providers provider);
    List<ProvidersBudgetSubcategories> findByBudgetSubcategorie(CBudgetSubcategories budgetSubcategories);
}