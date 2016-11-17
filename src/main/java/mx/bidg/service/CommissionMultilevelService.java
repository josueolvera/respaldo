package mx.bidg.service;

import java.io.IOException;
import mx.bidg.model.CommissionMultilevel;

import java.util.List;
import mx.bidg.model.Users;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by PC_YAIR on 17/11/2016.
 */
public interface CommissionMultilevelService {
    CommissionMultilevel findById (Integer idCommissionMultilevel);
    List<CommissionMultilevel> findAll();
    List<CommissionMultilevel> update (MultipartFile file, String calculateDate, Users user)throws IOException, InvalidFormatException;
    List<CommissionMultilevel> save (MultipartFile file, String calculateDate, Users user)throws IOException, InvalidFormatException;
    Boolean existsMultilevelRecord(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException;
}
