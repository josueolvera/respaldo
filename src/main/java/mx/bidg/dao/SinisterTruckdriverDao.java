package mx.bidg.dao;

import mx.bidg.model.SinisterTruckdriver;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
public interface SinisterTruckdriverDao extends InterfaceDao<SinisterTruckdriver> {
    List<SinisterTruckdriver> findByCreationDate(LocalDateTime creationDate);
}
