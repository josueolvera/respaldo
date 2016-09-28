package mx.bidg.service;

import mx.bidg.model.Checks;

import java.util.List;

/**
 * Created by gerardo8 on 27/09/16.
 */
public interface ChecksService {
    List<Checks> getChecks(Integer idUser);
}
