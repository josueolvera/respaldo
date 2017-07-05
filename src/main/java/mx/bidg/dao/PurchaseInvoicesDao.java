package mx.bidg.dao;

import mx.bidg.model.PurchaseInvoices;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
public interface PurchaseInvoicesDao extends InterfaceDao<PurchaseInvoices> {
    PurchaseInvoices findByIdRequest(Integer idRequest);
    List<PurchaseInvoices> findByRequestTypeAndCatgory(Integer idRequestCategory, Integer idRequestType, Integer idRequestStatus);
    List<PurchaseInvoices> findFolio (String folio);
    List<PurchaseInvoices> findBetweenDates (LocalDateTime fromDate, LocalDateTime toDate, Integer status);
}
