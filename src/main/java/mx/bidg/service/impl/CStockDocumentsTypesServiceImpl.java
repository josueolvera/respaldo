package mx.bidg.service.impl;

import mx.bidg.dao.CStockDocumentsTypesDao;
import mx.bidg.model.CStockDocumentsTypes;
import mx.bidg.service.CStockDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 29/12/15.
 */
@Service
@Transactional
public class CStockDocumentsTypesServiceImpl implements CStockDocumentsTypesService {

    @Autowired
    private CStockDocumentsTypesDao stockDocumentsTypesDao;

    @Override
    public List<CStockDocumentsTypes> findAll() {
        return stockDocumentsTypesDao.findAll();
    }
}
