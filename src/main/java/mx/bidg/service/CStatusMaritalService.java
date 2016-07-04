package mx.bidg.service;

import mx.bidg.model.CStatusMarital;

import java.util.List;

/**
 * Created by jolvera on 23/06/16.
 */
public interface CStatusMaritalService {
    List<CStatusMarital> findAll();
    CStatusMarital findById(Integer idStatusMarital);
}
