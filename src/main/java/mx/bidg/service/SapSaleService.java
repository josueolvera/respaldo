package mx.bidg.service;

import mx.bidg.model.SapSale;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
}
