package mx.bidg.service.impl;

import mx.bidg.dao.CContractTypeDao;
import mx.bidg.model.CContractType;
import mx.bidg.service.CContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
@Service
@Transactional
public class CContractTypeServiceImpl implements CContractTypeService {

    @Autowired
    CContractTypeDao cContractTypeDao;

    @Override
    public CContractType findById(Integer idContractType) {
        return cContractTypeDao.findById(idContractType);
    }

    @Override
    public List<CContractType> findAll() {
        return cContractTypeDao.findAll();
    }
}
