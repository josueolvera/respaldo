package mx.bidg.service;

import mx.bidg.model.CSubareas;

import java.util.List;

/**
 * Created by josue on 19/04/2017.
 */
public interface CSubareasService {

    CSubareas save(CSubareas subareas);
    CSubareas update(CSubareas subareas);
    CSubareas findById(Integer idCSubareas);
    List<CSubareas> findAll();
    boolean delete(CSubareas subareas);
}
