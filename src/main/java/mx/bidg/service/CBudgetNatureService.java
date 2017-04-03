package mx.bidg.service;

import mx.bidg.model.CBudgetNature;

import java.util.List;

/**
 * Created by Kevin Salvador on 03/04/2017.
 */
public interface CBudgetNatureService {
    public List<CBudgetNature> findAll();
    public CBudgetNature findById(Integer id);
}
