package mx.bidg.service.impl;

import mx.bidg.dao.CFieldsTableSalesDao;
import mx.bidg.model.CFieldsTableSales;
import mx.bidg.service.CFieldsTableSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 17/06/16.
 */
@Repository
@Transactional
public class CFieldsTableSalesServiceImpl implements CFieldsTableSalesService {

    @Autowired
    private CFieldsTableSalesDao fieldsTableSalesDao;

    @Override
    public List<CFieldsTableSales> findAll() {
        return fieldsTableSalesDao.findAll();
    }
}
