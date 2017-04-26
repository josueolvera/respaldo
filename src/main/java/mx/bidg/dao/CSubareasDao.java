package mx.bidg.dao;

import mx.bidg.model.CSubareas;

/**
 * Created by josue on 19/04/2017.
 */
public interface CSubareasDao extends InterfaceDao<CSubareas> {
    CSubareas findByIdSubarea(Integer idSubarea);
}
