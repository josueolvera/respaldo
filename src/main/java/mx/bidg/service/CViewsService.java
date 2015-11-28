package mx.bidg.service;

import mx.bidg.model.CViews;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 27/11/15.
 */
public interface CViewsService {
    List<CViews> findAll();
    CViews findById(int id);
}
