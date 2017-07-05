package mx.bidg.service;

import mx.bidg.model.PurchaseInvoices;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
public interface PurchaseInvoicesService {
    PurchaseInvoices save (PurchaseInvoices purchaseInvoices);
    PurchaseInvoices update(PurchaseInvoices purchaseInvoices);
    PurchaseInvoices findById(Integer idPurchaseInvoices);
    List<PurchaseInvoices> findAll();
    boolean delete(PurchaseInvoices purchaseInvoices);
    PurchaseInvoices findByIdRequest(Integer idRequest);
    List<PurchaseInvoices> findByRequestTypeAndCatgory(Integer idRequestCategory, Integer idRequestType, Integer idRequestStatus);
    List<PurchaseInvoices> findFolio (String folio);
    void payablesRequests(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate, Integer status) throws IOException;
}
