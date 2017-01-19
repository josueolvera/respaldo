package mx.bidg.service;

import mx.bidg.model.MrPayTruckDriver;

import java.util.List;

/**
 * Created by Kevin Salvador on 19/01/2017.
 */
public interface MrPayTruckDriverService {
    MrPayTruckDriver findById(Integer id);
    List<MrPayTruckDriver>findAll();
    MrPayTruckDriver save(MrPayTruckDriver mrPayTruckDriver);
    MrPayTruckDriver update(MrPayTruckDriver mrPayTruckDriver);
    boolean delete(MrPayTruckDriver mrPayTruckDriver);
}
