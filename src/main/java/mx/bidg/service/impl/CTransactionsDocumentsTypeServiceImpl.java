package mx.bidg.service.impl;

import mx.bidg.dao.CTransactionsDocumentsTypeDao;
import mx.bidg.model.CTransactionsDocumentsTypes;
import mx.bidg.service.CTransactionsDocumentsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jolvera on 10/06/16.
 */
@Service
public class CTransactionsDocumentsTypeServiceImpl implements CTransactionsDocumentsTypeService {

    @Autowired
    CTransactionsDocumentsTypeDao cTransactionsDocumentsTypeDao;

    @Override
    public List<CTransactionsDocumentsTypes> findAll() {
        return cTransactionsDocumentsTypeDao.findAll();
    }
}
