package mx.bidg.service;

import mx.bidg.model.EmployeesHistory;
import mx.bidg.model.Outsourcing;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.model.Users;

/**
 * Created by gerardo8 on 16/05/16.
 */
public interface OutsourcingService {
    List<Outsourcing> findAll();
    Outsourcing findById(Integer id);
    List<Outsourcing> updateFromExcel(MultipartFile file,String calculateDate, Users user) throws IOException, InvalidFormatException;
    Boolean existsOutsourcingRecord(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException;
    List<Outsourcing> saveFromExcel(MultipartFile file, String calculateDate, Users user)throws IOException, InvalidFormatException;
    List<Outsourcing> findByAllEmployeesAndApplicationDate(List<EmployeesHistory> employeesHistoryList, LocalDateTime initialDate, LocalDateTime finalDate);
    Outsourcing update (Outsourcing outsourcing);
}
