package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.CRooms;

/**
 *
 * @author rubens
 */
public interface CRoomsDao extends InterfaceDao<CRooms> {

    public List<CRooms> getRooms(Integer idRole);
    
}
