package mx.bidg.service;

import mx.bidg.model.CBudgetSubSubcategories;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/02/2017.
 */
public interface CBudgetSubSubcategoriesService {
    List<CBudgetSubSubcategories> findAll();
    CBudgetSubSubcategories findById(Integer id);
}
