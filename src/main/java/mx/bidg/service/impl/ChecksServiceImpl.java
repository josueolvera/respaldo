package mx.bidg.service.impl;

import mx.bidg.dao.ChecksDao;
import mx.bidg.model.Checks;
import mx.bidg.service.ChecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 27/09/16.
 */
@Service
@Transactional
public class ChecksServiceImpl implements ChecksService {

    @Autowired
    private ChecksDao checksDao;

    @Override
    public List<Checks> getChecks(Integer idUser) {
        return checksDao.getChecks(idUser);
    }
}
