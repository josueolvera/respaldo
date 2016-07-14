package mx.bidg.service;

import mx.bidg.model.CPlaneTicketsTypes;

import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
public interface CPlaneTicketsTypesService {
    List<CPlaneTicketsTypes> findAll();
    CPlaneTicketsTypes findById(Integer id);
    CPlaneTicketsTypes save(CPlaneTicketsTypes planeTicketType);
    CPlaneTicketsTypes update(CPlaneTicketsTypes planeTicketType);
    Boolean delete(CPlaneTicketsTypes planeTicketType);
}
