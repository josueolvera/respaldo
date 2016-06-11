package mx.bidg.service;

import mx.bidg.model.CIncidence;
import mx.bidg.model.CTicketsCategories;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
public interface CIncidenceService {

    CIncidence findById(int id);
    List<CIncidence> findAll(CTicketsCategories category);
}
