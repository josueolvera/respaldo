/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.io.IOException;
import java.util.List;
import mx.bidg.model.Commission_Emcofin;
import mx.bidg.model.Users;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Kevin Salvador
 */
public interface CommissionEmcofinService {
    List<Commission_Emcofin>findAll();
    Commission_Emcofin findById(Integer id);
    List<Commission_Emcofin>saveFromExcel(MultipartFile file, String calculateDate, Users user)throws IOException, InvalidFormatException;
    List<Commission_Emcofin>updateFromExcel(MultipartFile file, String calculateDate, Users user)throws IOException, InvalidFormatException;
    Boolean existsCommissionRecord(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException;
}

