package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CRooms;

/**
 *
 * @author rubens
 */
public interface CRoomsService {
    List<CRooms> findAll();
    CRooms findById(Integer idEstados);
    CRooms save(CRooms cRooms);
    CRooms update(CRooms cRooms);
    Boolean delete(CRooms cRooms);
}
