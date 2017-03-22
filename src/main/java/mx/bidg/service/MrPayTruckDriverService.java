package mx.bidg.service;

import mx.bidg.model.MrPayTruckDriver;
import mx.bidg.pojos.PdfAternaPojo;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Kevin Salvador on 19/01/2017.
 */
public interface MrPayTruckDriverService {
    MrPayTruckDriver findById(Integer id);
    List<MrPayTruckDriver>findAll();
    MrPayTruckDriver save(MrPayTruckDriver mrPayTruckDriver);
    MrPayTruckDriver update(MrPayTruckDriver mrPayTruckDriver);
    boolean delete(MrPayTruckDriver mrPayTruckDriver);
    MrPayTruckDriver findAuthorizationNumber(String authorizationNumber);
    void readCsv(String fileName);
    List<MrPayTruckDriver> findByPaymentDate (LocalDate paymentDate);
    List<MrPayTruckDriver> findByPaymentDateBetween (LocalDate startDate, LocalDate endDate);
    List<String> findNoAutorizationByDStartValidityBetween(LocalDate starDate, LocalDate finalDate);
    PdfAternaPojo conciliationByAutorizationNumber(List<String> noAutorizationMrPay, List<String> noAutorizationApp, LocalDate starDate, LocalDate finalDate);
}
