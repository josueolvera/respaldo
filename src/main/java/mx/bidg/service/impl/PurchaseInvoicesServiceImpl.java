package mx.bidg.service.impl;

import mx.bidg.dao.PurchaseInvoicesDao;
import mx.bidg.model.PurchaseInvoices;
import mx.bidg.service.PurchaseInvoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
@Service
@Transactional
public class PurchaseInvoicesServiceImpl implements PurchaseInvoicesService {

    @Autowired
    PurchaseInvoicesDao purchaseInvoicesDao;

    @Override
    public PurchaseInvoices save(PurchaseInvoices purchaseInvoices) {
        return purchaseInvoicesDao.save(purchaseInvoices);
    }

    @Override
    public PurchaseInvoices update(PurchaseInvoices purchaseInvoices) {
        return purchaseInvoicesDao.update(purchaseInvoices);
    }

    @Override
    public PurchaseInvoices findById(Integer idPurchaseInvoices) {
        return purchaseInvoicesDao.findById(idPurchaseInvoices);
    }

    @Override
    public List<PurchaseInvoices> findAll() {
        return purchaseInvoicesDao.findAll();
    }

    @Override
    public boolean delete(PurchaseInvoices purchaseInvoices) {
        return purchaseInvoicesDao.delete(purchaseInvoices);
    }
}
