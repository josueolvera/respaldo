package mx.bidg.service;

import mx.bidg.model.PurchaseInvoicesFiles;

import java.util.List;

/**
 * Created by desarrollador on 14/06/17.
 */
public interface PurchaseInvoicesFilesService {
    PurchaseInvoicesFiles save(PurchaseInvoicesFiles purchaseInvoicesFiles);
    PurchaseInvoicesFiles update(PurchaseInvoicesFiles purchaseInvoicesFiles);
    PurchaseInvoicesFiles findById(Integer idPurchaseInvoicesFiles);
    List<PurchaseInvoicesFiles> findAll();
    boolean delete(PurchaseInvoicesFiles purchaseInvoicesFiles);
    List<PurchaseInvoicesFiles> findByIdPurchaseInvoices(Integer idPurchaseInvoices);
}
