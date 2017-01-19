package mx.bidg.service.impl;

import mx.bidg.dao.CAttachmentTypeDao;
import mx.bidg.model.CAttachmentType;
import mx.bidg.service.CAttachmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 17/01/2017.
 */
@Service
@Transactional
public class CAttachmentTypeServiceImpl implements CAttachmentTypeService {

    @Autowired
    private CAttachmentTypeDao cAttachmentTypeDao;

    @Override
    public List<CAttachmentType> findAll() {
        return cAttachmentTypeDao.findAll();
    }

    @Override
    public CAttachmentType findById(Integer id) {
        return cAttachmentTypeDao.findById(id);
    }
}
