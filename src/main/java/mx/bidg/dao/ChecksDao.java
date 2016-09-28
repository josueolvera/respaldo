package mx.bidg.dao;

import mx.bidg.model.Checks;

import java.util.List;

/**
 * Created by gerardo8 on 27/09/16.
 */
public interface ChecksDao extends InterfaceDao<Checks> {
    List<Checks> getChecks(Integer idUser);
}
