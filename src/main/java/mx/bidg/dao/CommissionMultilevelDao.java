package mx.bidg.dao;

import java.time.LocalDateTime;
import mx.bidg.model.CommissionMultilevel;

/**
 * Created by PC_YAIR on 17/11/2016.
 */
public interface CommissionMultilevelDao extends InterfaceDao<CommissionMultilevel> {
    CommissionMultilevel finfByidEmployee(int idEmployee,LocalDateTime applicationDate);
}
