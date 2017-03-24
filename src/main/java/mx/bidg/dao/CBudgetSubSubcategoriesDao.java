package mx.bidg.dao;

import mx.bidg.model.CBudgetSubSubcategories;

import java.util.List;

/**
 * Created by Kevin Salvador on 14/02/2017.
 */
public interface CBudgetSubSubcategoriesDao extends InterfaceDao<CBudgetSubSubcategories> {

    List<CBudgetSubSubcategories> findByThirdLevel (String thirdLevel);
}
