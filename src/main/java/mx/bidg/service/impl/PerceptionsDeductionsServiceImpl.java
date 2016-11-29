package mx.bidg.service.impl;

import mx.bidg.dao.PerceptionsDeductionsDao;
import mx.bidg.dao.SqlQueriesDao;
import mx.bidg.model.PerceptionsDeductions;
import mx.bidg.model.SqlQueries;
import mx.bidg.model.Users;
import mx.bidg.service.PerceptionsDeductionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Service
@Transactional
public class PerceptionsDeductionsServiceImpl implements PerceptionsDeductionsService {

    @Autowired
    PerceptionsDeductionsDao perceptionsDeductionsDao;

    @Autowired
    SqlQueriesDao sqlQueriesDao;

    @Override
    public PerceptionsDeductions save(PerceptionsDeductions perceptionsDeductions) {
        return perceptionsDeductionsDao.save(perceptionsDeductions);
    }

    @Override
    public PerceptionsDeductions update(PerceptionsDeductions perceptionsDeductions) {
        return perceptionsDeductionsDao.update(perceptionsDeductions);
    }

    @Override
    public PerceptionsDeductions findById(Integer idPerceptionsDeductions) {
        return perceptionsDeductionsDao.findById(idPerceptionsDeductions);
    }

    @Override
    public List<PerceptionsDeductions> findAll() {
        return perceptionsDeductionsDao.findAll();
    }

    @Override
    public boolean delete(PerceptionsDeductions perceptionsDeductions) {
        perceptionsDeductionsDao.delete(perceptionsDeductions);
        return true;
    }

    @Override
    public List<PerceptionsDeductions> findAllActives() {
        return perceptionsDeductionsDao.findAllWithStatus();
    }

    @Override
    public List<PerceptionsDeductions> calculateBonus(Users user, String ofDate, String untilDate) {
        SqlQueries sqlQuery = sqlQueriesDao.findQuery(3);
        perceptionsDeductionsDao.calculateBonus(sqlQuery,user,ofDate,untilDate);
        return perceptionsDeductionsDao.findAll();
    }
}
