package mx.bidg.service;

import mx.bidg.model.RequestsDates;

import java.util.List;

/**
 * Created by jcesar on 12/06/2017.
 */
public interface RequestsDatesService {
    RequestsDates save(RequestsDates requestsDates);
    RequestsDates update(RequestsDates requestsDates);
    RequestsDates findById(Integer idRequestsDates);
    List<RequestsDates> findAll();
    boolean delete(RequestsDates requestsDates);
    RequestsDates getByRequest(Integer idRequest);
}
