package mx.bidg.service.impl;

import mx.bidg.dao.CSapFileDao;
import mx.bidg.model.CSapFile;
import mx.bidg.service.CSapFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 12/05/16.
 */
@Transactional
@Service
public class CSapFileServiceImpl implements CSapFileService {

    @Autowired
    CSapFileDao cSapFileDao;

    @Override
    public List<CSapFile> findAll() {
        return cSapFileDao.findAll();
    }

    @Override
    public CSapFile findById(Integer idSapFile) {
        return cSapFileDao.findById(idSapFile);
    }

    @Override
    public CSapFile update(MultipartFile file, Integer idSapFile) {
        CSapFile cSapFile = cSapFileDao.findById(idSapFile);

        cSapFile.setFileName(file.getOriginalFilename());
        cSapFile.setLastUploadedDate(LocalDateTime.now());

        return cSapFileDao.update(cSapFile);
    }
}
