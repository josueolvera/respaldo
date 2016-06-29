package mx.bidg.service.impl;

import mx.bidg.dao.CEducationDao;
import mx.bidg.model.CEducation;
import mx.bidg.service.CEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 22/06/16.
 */
@Service
@Transactional
public class CEducationServiceImpl implements CEducationService {

    @Autowired
    CEducationDao cEducationDao;

    @Override
    public List<CEducation> findAll() {
        return cEducationDao.findAll();
    }
}
