package mx.bidg.service;

import mx.bidg.model.SinisterTruckdriver;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
public interface SinisterTruckdriverService {

    SinisterTruckdriver save (SinisterTruckdriver sinisterTruckdriver);
    SinisterTruckdriver update(SinisterTruckdriver sinisterTruckdriver);
    SinisterTruckdriver findByid (Integer idST);
    List<SinisterTruckdriver> findAll ();
    boolean delete (SinisterTruckdriver sinisterTruckdriver);
    List<SinisterTruckdriver>findByCreationDate(LocalDateTime creationDate);
}
