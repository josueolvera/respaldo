package mx.bidg.service;

import mx.bidg.model.CIncidence;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
public interface CIncidenceService {

    CIncidence findById(int id);
    List<CIncidence> findAll();
}
