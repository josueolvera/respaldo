package mx.bidg.service.impl;

import mx.bidg.dao.CVoucherTypesDao;
import mx.bidg.model.CVoucherTypes;
import mx.bidg.service.CVoucherTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 21/07/16.
 */
@Transactional
@Service
public class CVoucherTypesServiceImpl implements CVoucherTypesService {

    @Autowired
    private CVoucherTypesDao cVoucherTypesDao;

    @Override
    public List<CVoucherTypes> findAll() {
        return cVoucherTypesDao.findAll();
    }
}
