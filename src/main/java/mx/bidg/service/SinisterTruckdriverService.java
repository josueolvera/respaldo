package mx.bidg.service;

import mx.bidg.model.SinisterTruckdriver;
import mx.bidg.pojos.FolioAmountTruckdriverPojo;
import mx.bidg.pojos.PdfAternaPojo;

import java.time.LocalDate;
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
    List<SinisterTruckdriver> findByCreationDate(LocalDateTime creationDate);
    void readCsv(String fileName);
    List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate);
    List<SinisterTruckdriver> findByDStartValidity(LocalDate startDate, LocalDate endDate);
    PdfAternaPojo conciliationByFolio(List<String> foliosApp, List<String> foliosImpro, LocalDate startDate, LocalDate endDate);
    List<FolioAmountTruckdriverPojo> getFoliosComisionIvaByDStartValidity (LocalDate startDate, LocalDate endDate);
}
