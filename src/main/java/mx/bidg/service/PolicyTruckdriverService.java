package mx.bidg.service;

import mx.bidg.model.PolicyTruckdriver;
import org.exolab.castor.types.Date;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
public interface PolicyTruckdriverService  {
    PolicyTruckdriver save(PolicyTruckdriver policyTruckdriver);
    PolicyTruckdriver update(PolicyTruckdriver policyTruckdriver);
    PolicyTruckdriver findByid (Integer idPTD);
    List<PolicyTruckdriver> findByDStartValidity(LocalDate startdate);
    List<PolicyTruckdriver>findAll();
    boolean delete (PolicyTruckdriver policyTruckdriver);
    }
