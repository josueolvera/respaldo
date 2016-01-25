package mx.bidg.service.impl;

import mx.bidg.dao.CArticleStatusDao;
import mx.bidg.model.CArticleStatus;
import mx.bidg.service.CArticleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 25/01/16.
 */
@Service
@Transactional
public class CArticleStatusServiceImpl implements CArticleStatusService {

    @Autowired
    private CArticleStatusDao statusDao;

    @Override
    public List<CArticleStatus> findAll() {
        return statusDao.findAll();
    }
}
