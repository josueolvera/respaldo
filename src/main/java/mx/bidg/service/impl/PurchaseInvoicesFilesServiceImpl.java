package mx.bidg.service.impl;

import mx.bidg.dao.PurchaseInvoicesFilesDao;
import mx.bidg.model.PurchaseInvoicesFiles;
import mx.bidg.service.PurchaseInvoicesFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by desarrollador on 14/06/17.
 */
@Service
@Transactional
public class PurchaseInvoicesFilesServiceImpl implements PurchaseInvoicesFilesService {

    @Autowired
    private PurchaseInvoicesFilesDao purchaseInvoicesFilesDao;

    @Override
    public PurchaseInvoicesFiles save(PurchaseInvoicesFiles purchaseInvoicesFiles) {
        return purchaseInvoicesFilesDao.save(purchaseInvoicesFiles);
    }

    @Override
    public PurchaseInvoicesFiles update(PurchaseInvoicesFiles purchaseInvoicesFiles) {
        return purchaseInvoicesFilesDao.update(purchaseInvoicesFiles);
    }

    @Override
    public PurchaseInvoicesFiles findById(Integer idPurchaseInvoicesFiles) {
        return purchaseInvoicesFilesDao.findById(idPurchaseInvoicesFiles);
    }

    @Override
    public List<PurchaseInvoicesFiles> findAll() {
        return purchaseInvoicesFilesDao.findAll();
    }

    @Override
    public boolean delete(PurchaseInvoicesFiles purchaseInvoicesFiles) {
        return purchaseInvoicesFilesDao.delete(purchaseInvoicesFiles);
    }

    @Override
    public List<PurchaseInvoicesFiles> findByIdPurchaseInvoices(Integer idPurchaseInvoices) {
        return purchaseInvoicesFilesDao.findByIdPurchaseInvoices(idPurchaseInvoices);
    }
}
