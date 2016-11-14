package mx.bidg.service.impl;

import mx.bidg.dao.CVacationPayTabDao;
import mx.bidg.model.CVacationPayTab;
import mx.bidg.service.CVacationPayTabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by PC_YAIR on 14/11/2016.
 */
@Service
@Transactional
public class CVacationPayTabServiceImpl implements CVacationPayTabService {

    @Autowired
    CVacationPayTabDao vacationPayTabDao;

    @Override
    public CVacationPayTab save(CVacationPayTab cVacationPayTab) {
        return vacationPayTabDao.save(cVacationPayTab);
    }

    @Override
    public CVacationPayTab update(CVacationPayTab cVacationPayTab) {
        return vacationPayTabDao.update(cVacationPayTab);
    }

    @Override
    public CVacationPayTab findById(Integer idCVacationPayTab) {
        return vacationPayTabDao.findById(idCVacationPayTab);
    }

    @Override
    public boolean delete(CVacationPayTab cVacationPayTab) {
        vacationPayTabDao.delete(cVacationPayTab);
        return true;
    }

    @Override
    public List<CVacationPayTab> findAll() {
        return vacationPayTabDao.findAll();
    }
}
