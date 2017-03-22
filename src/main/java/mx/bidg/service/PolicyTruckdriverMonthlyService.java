package mx.bidg.service;

import mx.bidg.model.PolicyTruckdriver;
import mx.bidg.model.PolicyTruckdriverMonthly;
import mx.bidg.pojos.PdfAternaPojo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Desarrollador on 18/01/2017.
 */
public interface PolicyTruckdriverMonthlyService {

    PolicyTruckdriverMonthly save(PolicyTruckdriverMonthly policyTruckdriverMonthly);
    PolicyTruckdriverMonthly update(PolicyTruckdriverMonthly policyTruckdriverMonthly);
    PolicyTruckdriverMonthly findById(Integer idPolicyTruckdriverMonthly);
    List<PolicyTruckdriverMonthly> findAll();
    boolean delete(PolicyTruckdriverMonthly policyTruckdriverMonthly);
    boolean existsPolicyTruckDriverRecord(MultipartFile file, Integer idUser) throws IOException;
    List<PolicyTruckdriverMonthly> saveFromCsv(MultipartFile file, Integer idUser)throws IOException;
    void readCsvAlterna(String fileName);
    List<PolicyTruckdriverMonthly> findDStartValidityBetween(LocalDate starDate, LocalDate finalDate);
    PdfAternaPojo validateAmounts(List listaApp, List listaAterna);
    List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate);
    List findFoliosCommissionIvaByDStartValidity(LocalDate startDate, LocalDate endDate);
}
