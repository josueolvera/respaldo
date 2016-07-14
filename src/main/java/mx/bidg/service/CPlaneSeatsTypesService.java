package mx.bidg.service;

import mx.bidg.model.CPlaneSeatsTypes;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface CPlaneSeatsTypesService {

    List<CPlaneSeatsTypes> findAll();
    CPlaneSeatsTypes findById(Integer id);
    CPlaneSeatsTypes save(CPlaneSeatsTypes planeSeatType);
    CPlaneSeatsTypes update(CPlaneSeatsTypes planeSeatType);
    Boolean delete(CPlaneSeatsTypes planeSeatType);
}
