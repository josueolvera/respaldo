package mx.bidg.service;

import mx.bidg.model.CsbPayCommission;
import mx.bidg.model.Users;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Serch on 29/06/2017.
 */
public interface CsbPayCommissionService {
    CsbPayCommission save(CsbPayCommission csbPayCommission);
    CsbPayCommission update(CsbPayCommission csbPayCommission);
    CsbPayCommission findById(Integer idCsbPayCommission);
    List<CsbPayCommission> findAll();
    boolean delete(CsbPayCommission csbPayCommission);
    Boolean existsSales(MultipartFile file) throws IOException, InvalidFormatException;
    List<CsbPayCommission> saveFromExcel(MultipartFile file, Users user) throws IOException, InvalidFormatException;
    List<CsbPayCommission> updateFromExcel(MultipartFile file, Users user) throws IOException, InvalidFormatException;
}
