package mx.bidg.dao;

import mx.bidg.model.SinisterTruckdriver;

import java.util.Date;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
public interface SinisterTruckdriverDao extends InterfaceDao<SinisterTruckdriver> {
    List<SinisterTruckdriver>findByDateStart(Integer idTipeAssistance,String startDate);
}
