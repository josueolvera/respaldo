package mx.bidg.service;

import mx.bidg.model.Flights;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface FlightsService {

    List<Flights> findAll();
    Flights findById(Integer id);
    Flights save(Flights flight);
    Flights update(Flights flight);
    Boolean delete(Flights flight);
}
