package mx.bidg.dao;

import mx.bidg.model.RequestHistory;

import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
public interface RequestHistoryDao extends InterfaceDao<RequestHistory> {
    List<RequestHistory> findAllByRequest(Integer idRequest);
}
