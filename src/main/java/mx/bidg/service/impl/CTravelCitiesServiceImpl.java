package mx.bidg.service.impl;

import mx.bidg.dao.CTravelCitiesDao;
import mx.bidg.model.CTravelCities;
import mx.bidg.service.CTravelCitiesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public class CTravelCitiesServiceImpl implements CTravelCitiesService {

    @Autowired
    private CTravelCitiesDao cTravelCitiesDao;

    @Override
    public List<CTravelCities> findAll() {
        return cTravelCitiesDao.findAll();
    }

    @Override
    public CTravelCities findById(Integer id) {
        return cTravelCitiesDao.findById(id);
    }

    @Override
    public CTravelCities save(CTravelCities travelCity) {
        return cTravelCitiesDao.save(travelCity);
    }

    @Override
    public CTravelCities update(CTravelCities travelCity) {
        return cTravelCitiesDao.update(travelCity);
    }

    @Override
    public Boolean delete(CTravelCities travelCity) {
        return cTravelCitiesDao.delete(travelCity);
    }
}
