package mx.bidg.service.impl;

import mx.bidg.dao.CRefundConceptDocumentsTypesDao;
import mx.bidg.model.CRefundConceptDocumentsTypes;
import mx.bidg.service.CRefundConceptDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo8 on 22/07/16.
 */
@Service
@Transactional
public class CRefundConceptDocumentsTypesServiceImpl implements CRefundConceptDocumentsTypesService {

    @Autowired
    private CRefundConceptDocumentsTypesDao cRefundConceptDocumentsTypesDao;

    @Override
    public List<CRefundConceptDocumentsTypes> findByIdVoucherType(Integer idVoucherType) {
        return cRefundConceptDocumentsTypesDao.findByIdVoucherType(idVoucherType);
    }

    @Override
    public List<CRefundConceptDocumentsTypes> findAll() {
        return cRefundConceptDocumentsTypesDao.findAll();
    }
}
