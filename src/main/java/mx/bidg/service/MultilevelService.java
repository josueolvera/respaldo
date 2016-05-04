package mx.bidg.service;

import mx.bidg.model.Multilevel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 28/04/16.
 */
public interface MultilevelService {
    List<Multilevel> findAll();
    Multilevel findById(Integer id);
    List<Multilevel> saveFromExcel(MultipartFile file) throws IOException, InvalidFormatException;
}
