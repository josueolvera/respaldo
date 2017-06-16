package mx.bidg.dao;

import mx.bidg.model.PurchaseInvoicesFiles;

import java.util.List;

/**
 * Created by desarrollador on 14/06/17.
 */
public interface PurchaseInvoicesFilesDao extends InterfaceDao<PurchaseInvoicesFiles> {
    List<PurchaseInvoicesFiles> findByIdPurchaseInvoices(Integer idPurchaseInvoices);
}
