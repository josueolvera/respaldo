package mx.bidg.dao;

import mx.bidg.model.MrPayTruckDriver;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Kevin Salvador on 19/01/2017.
 */
public interface MrPayTruckDriverDao extends InterfaceDao<MrPayTruckDriver> {
    MrPayTruckDriver findAuthorizationNumber(String authorizationNumber);
    List<MrPayTruckDriver> findByPaymentDate(LocalDate paymentDate);
    List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate);
    List<MrPayTruckDriver> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);
}
