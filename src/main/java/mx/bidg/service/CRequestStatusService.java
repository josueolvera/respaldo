package mx.bidg.service;

import mx.bidg.model.CRequestStatus;

import java.util.List;

/**
 * Created by desarrollador on 2/06/17.
 */
public interface CRequestStatusService {
    CRequestStatus save(CRequestStatus cRequestStatus);
    CRequestStatus update(CRequestStatus cRequestStatus);
    CRequestStatus findById(Integer idCRequestStatus);
    List<CRequestStatus> findAll();
    boolean delete(CRequestStatus cRequestStatus);
}
