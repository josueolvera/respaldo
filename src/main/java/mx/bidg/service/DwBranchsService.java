package mx.bidg.service;

import mx.bidg.model.DwBranchs;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
public interface DwBranchsService {

    List<DwBranchs> findAll();
    DwBranchs findById(Integer id);
    List<DwBranchs> saveFromExcel(MultipartFile file) throws IOException, InvalidFormatException;
    List<DwBranchs> updateFromExcel(MultipartFile file) throws IOException, InvalidFormatException;
}
