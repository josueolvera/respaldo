package mx.bidg.dao;

import mx.bidg.model.CRequestStatus;

/**
 * Created by gerardo8 on 03/10/16.
 */
public interface CRequestStatusDao extends InterfaceDao<CRequestStatus> {
    CRequestStatus findById(int id);
}
