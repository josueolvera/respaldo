package mx.bidg.service;

import mx.bidg.model.Flights;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface FlightsService {

    List<Flights> findAll();
    Flights findById(Integer id);
    Flights save(String data, Integer idPlaneTicket) throws IOException;
    Flights update(Flights flight);
    Boolean delete(Flights flight);
}
