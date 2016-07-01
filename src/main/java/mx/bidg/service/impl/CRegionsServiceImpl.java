package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CRegionsDao;
import mx.bidg.model.CRegions;
import mx.bidg.service.CRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CRegionsServiceImpl implements CRegionsService {

    @Autowired
    private CRegionsDao cRegionsDao;
    
    @Override
    public List<CRegions> findAll() {
        return cRegionsDao.findAll();
    }
    
}
