package mx.bidg.dao;

import mx.bidg.model.RequestOrderDetail;

import java.util.List;

/**
 * Created by desarrollador on 11/06/17.
 */
public interface RequestOrderDetailDao extends InterfaceDao<RequestOrderDetail> {
    List<RequestOrderDetail> findByidReqOrderDocument(Integer idRequestOrderDocument);
}
