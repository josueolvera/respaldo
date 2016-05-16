package mx.bidg.service;

import mx.bidg.model.CSapFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by gerardo8 on 12/05/16.
 */
public interface CSapFileService {

    List<CSapFile> findAll();
    CSapFile findById(Integer idSapFile);
    CSapFile update(MultipartFile file,Integer idSapFile);
    
}
