package mx.bidg.dao;

import mx.bidg.model.CsbPayCommission;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Serch on 29/06/2017.
 */
public interface CsbPayCommissionDao extends InterfaceDao<CsbPayCommission> {
    CsbPayCommission findByIdSale(String idSale);
    List<CsbPayCommission> findAllByIdSale(String idSale);
    BigDecimal sumAmountByDateAndClaveSap(String claveSap, LocalDate initialDate, LocalDate finalDate);
}
