package mx.bidg.dao;

import mx.bidg.model.PolicyTruckdriverMonthly;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Desarrollador on 18/01/2017.
 */
public interface PolicyTruckdriverMonthlyDao extends InterfaceDao<PolicyTruckdriverMonthly> {
    PolicyTruckdriverMonthly findByLicensePlateUserAndDate(String numLicensePlate, Integer idUser, LocalDate dStartValidity);
    List<PolicyTruckdriverMonthly> findDStartValidityBetween(LocalDate starDate, LocalDate finalDate);
    List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate);
    List findFoliosCommissionIvaByDStartValidity(LocalDate startDate, LocalDate endDate);
}
