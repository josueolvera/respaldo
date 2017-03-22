package mx.bidg.dao;

import mx.bidg.model.SinisterTruckdriver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
public interface SinisterTruckdriverDao extends InterfaceDao<SinisterTruckdriver> {
    List<SinisterTruckdriver> findByCreationDate(LocalDateTime creationDate);
    List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate);
    List<SinisterTruckdriver> findByDStartValidity(LocalDate startDate, LocalDate endDate);
    List getFoliosComisionIvaByDStartValidity(LocalDate startDate, LocalDate endDate);
}
