package mx.bidg.service;

import mx.bidg.model.CTravelCities;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface CTravelCitiesService {

    List<CTravelCities> findAll();
    CTravelCities findById(Integer id);
    CTravelCities save(CTravelCities travelCity);
    CTravelCities update(CTravelCities travelCity);
    Boolean delete(CTravelCities travelCity);
}
