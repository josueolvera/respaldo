package mx.bidg.service;

import mx.bidg.model.Passengers;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface PassengersService {
    List<Passengers> findAll();
    Passengers findById(Integer id);
    Passengers save(Passengers passenger);
    Passengers update(Passengers passenger);
    Boolean delete(Passengers passenger);
}
