package mx.bidg.service.impl;

import mx.bidg.dao.CTypePolicyDao;
import mx.bidg.model.CTypePolicy;
import mx.bidg.service.CTypePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kevin Salvador on 11/01/2017.
 */
@Service
@Transactional
public class CTypePolicyServiceImpl implements CTypePolicyService{

    @Autowired
    private CTypePolicyDao cTypePolicyDao;

    @Override
    public List<CTypePolicy> findAll() {
        return cTypePolicyDao.findAll();
    }
}
