package mx.bidg.service;

import mx.bidg.model.CommissionMultilevel;

import java.util.List;

/**
 * Created by PC_YAIR on 17/11/2016.
 */
public interface CommissionMultilevelService {
    CommissionMultilevel findById (Integer idCommissionMultilevel);
    List<CommissionMultilevel> findAll();
    CommissionMultilevel update (CommissionMultilevel commissionMultilevel);
    CommissionMultilevel save (CommissionMultilevel commissionMultilevel);
    boolean delete (CommissionMultilevel commissionMultilevel);
}
