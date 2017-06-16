package mx.bidg.dao;

import mx.bidg.model.PurchaseInvoices;

/**
 * Created by desarrollador on 31/05/17.
 */
public interface PurchaseInvoicesDao extends InterfaceDao<PurchaseInvoices> {
    PurchaseInvoices findByIdRequest(Integer idRequest);
}
