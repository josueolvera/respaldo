package mx.bidg.service;

import mx.bidg.model.SapSale;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 29/04/16.
 */
public interface SapSaleService {
    List<SapSale> findAll();
    SapSale findById(Integer id);
    List<SapSale> saveFromExcel(MultipartFile file) throws IOException, InvalidFormatException;
    List<SapSale> updateFromExcel(MultipartFile file) throws IOException, InvalidFormatException;
    Boolean existsSales(MultipartFile file) throws IOException, InvalidFormatException;
    List findByAgreementGroup(Integer idAg, LocalDateTime fromDate, LocalDateTime toDate);
    List findByBranchGroup(Integer idAg, LocalDateTime fromDate, LocalDateTime toDate);
    List findByZonaGroup(Integer idAg, LocalDateTime fromDate, LocalDateTime toDate);
    List findByRegionGroup(Integer idAg, LocalDateTime fromDate, LocalDateTime toDate);
    List findByDistributorGroup(Integer idAg, LocalDateTime fromDate, LocalDateTime toDate);
    List findBySupervisorAndRleGroup(Integer idEmployee, Integer idAg, LocalDateTime fromDate, LocalDateTime toDate);
    List<String> getAllSaleStatus();
    List<SapSale> findAllSalesByStatusAndDates(List<String> status, String startDate, String endDate) throws Exception;
    SapSale assignSaleToEmployee(String idSale, String claveSap);
}
