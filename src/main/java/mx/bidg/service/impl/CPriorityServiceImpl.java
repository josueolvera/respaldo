package mx.bidg.service.impl;

import mx.bidg.dao.CPriorityDao;
import mx.bidg.model.CPriority;
import mx.bidg.service.CPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
@Transactional
@Service
public class CPriorityServiceImpl implements CPriorityService {

    @Autowired
    private CPriorityDao cPriorityDao;

    @Override
    public CPriority findById(int id) {
        return cPriorityDao.findById(id);
    }

    @Override
    public List<CPriority> findAll() {
        return cPriorityDao.findAll();
    }
}
