package mx.bidg.service;

import mx.bidg.model.CTravelTypes;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface CTravelTypesService {

    CTravelTypes save (CTravelTypes cTravelTypes);
    CTravelTypes update (CTravelTypes cTravelTypes);
    CTravelTypes findById (Integer idTravelType);
    List<CTravelTypes> findAll ();
    Boolean delete (CTravelTypes cTravelTypes);
}
