package mx.bidg.service;

import mx.bidg.model.Passengers;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface PassengersService {
    List<Passengers> findAll();
    Passengers findById(Integer id);
    Passengers save(String data, Integer idPassenger) throws IOException;
    Passengers update(Passengers passenger);
    Boolean delete(Passengers passenger);
}
