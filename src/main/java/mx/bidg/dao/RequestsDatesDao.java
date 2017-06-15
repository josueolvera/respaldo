package mx.bidg.dao;

import mx.bidg.model.RequestsDates;

import java.util.List;

/**
 * Created by jcesar on 12/06/2017.
 */
public interface RequestsDatesDao extends InterfaceDao<RequestsDates> {
    RequestsDates findByIdRequestsDates(Integer idResquestDates);
    List<RequestsDates> findAll();
}
