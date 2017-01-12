package mx.bidg.dao;

import mx.bidg.model.PolicyTruckdriver;
import org.exolab.castor.types.Date;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
public interface PolicyTruckdriverDao extends InterfaceDao<PolicyTruckdriver> {
    List<PolicyTruckdriver> findDStartValidity(LocalDate starDate);

}