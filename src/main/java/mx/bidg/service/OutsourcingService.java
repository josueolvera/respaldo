package mx.bidg.service;

import mx.bidg.model.Outsourcing;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
public interface OutsourcingService {
    List<Outsourcing> findAll();
    Outsourcing findById(Integer id);
    List<Outsourcing> saveFromExcel(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException;
    List<Outsourcing> updateFromExcel(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException;
    Boolean existsOutsourcingRecord(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException;
}
