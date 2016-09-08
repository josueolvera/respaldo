package mx.bidg.service.impl;

import mx.bidg.dao.ProvidersBudgetSubcategoriesDao;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersBudgetSubcategories;
import mx.bidg.service.ProvidersBudgetSubcategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import mx.bidg.model.CBudgetSubcategories;

/**
 * Created by jolvera on 30/05/16.
 */
@Service
@Transactional
public class ProvidersBudgetSubcategoriesServiceImpl implements ProvidersBudgetSubcategoriesService {

    @Autowired
    ProvidersBudgetSubcategoriesDao providersBudgetSubcategoriesDao;

    @Override
    public ProvidersBudgetSubcategories save(ProvidersBudgetSubcategories providersBudgetSubcategories) {
        return providersBudgetSubcategoriesDao.save(providersBudgetSubcategories);
    }

    @Override
    public ProvidersBudgetSubcategories update(ProvidersBudgetSubcategories providersBudgetSubcategories) {
        return providersBudgetSubcategoriesDao.update(providersBudgetSubcategories);
    }

    @Override
    public boolean delete(ProvidersBudgetSubcategories providersBudgetSubcategories) {
        providersBudgetSubcategoriesDao.delete(providersBudgetSubcategories);
        return true;
    }

    @Override
    public List<ProvidersBudgetSubcategories> findAll() {
        return providersBudgetSubcategoriesDao.findAll();
    }

    @Override
    public ProvidersBudgetSubcategories findById(int idProvidersProductsTypes) {
        return providersBudgetSubcategoriesDao.findById(idProvidersProductsTypes);
    }

    @Override
    public List<ProvidersBudgetSubcategories> findByProvider(Providers provider) {
        return providersBudgetSubcategoriesDao.findByProvider(provider);
    }

    @Override
    public List<ProvidersBudgetSubcategories> findByBudgetSubcategorie(CBudgetSubcategories budgetSubcategories) {
        return providersBudgetSubcategoriesDao.findByBudgetSubcategorie(budgetSubcategories);
    }
}
