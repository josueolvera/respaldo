package mx.bidg.service.impl;

import mx.bidg.dao.CAgreementsDao;
import mx.bidg.model.CAgreements;
import mx.bidg.service.CAgreementsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
@Service
@Transactional
public class CAgreementsServiceImpl implements CAgreementsService {

    @Autowired
    CAgreementsDao cAgreementsDao;

    @Override
    public CAgreements save(CAgreements cAgreements) {
        cAgreementsDao.save(cAgreements);
        return cAgreements;
    }

    @Override
    public CAgreements update(CAgreements cAgreements) {
        cAgreementsDao.update(cAgreements);
        return cAgreements;
    }

    @Override
    public CAgreements findById(Integer idAgreement) {
        return cAgreementsDao.findById(idAgreement);
    }

    @Override
    public List<CAgreements> findAll() {
        return cAgreementsDao.findAll();
    }

    @Override
    public boolean delete(CAgreements cAgreements) {
        return cAgreementsDao.delete(cAgreements);
    }

    @Override
    public boolean diferentAgreement(String agreementName) {

        boolean flag = false;

        if (cAgreementsDao.findByName(agreementName) != null) {
            flag = true;
        }

        return flag;
    }

    @Override
    public void lowDate (Integer idAgreement) {
        CAgreements agreement = cAgreementsDao.findById(idAgreement);
        agreement.setLowDate(LocalDateTime.now());
        cAgreementsDao.update(agreement);
    }
}
