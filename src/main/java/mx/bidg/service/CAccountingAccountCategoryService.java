package mx.bidg.service;

import mx.bidg.model.CAccountingAccountCategory;

import java.util.List;

/**
 * Created by josue on 7/07/16.
 */
public interface CAccountingAccountCategoryService {
    List<CAccountingAccountCategory> findAll();
    CAccountingAccountCategory findById(Integer idAccountingAccountCategory);
}
