package mx.bidg.service;

import mx.bidg.model.RequestOrderDetail;

import java.util.List;

/**
 * Created by desarrollador on 11/06/17.
 */
public interface RequestOrderDetailService {
    RequestOrderDetail save(RequestOrderDetail requestOrderDetail);
    RequestOrderDetail update(RequestOrderDetail requestOrderDetail);
    RequestOrderDetail findById(Integer idRequestOrderDetail);
    List<RequestOrderDetail> findAll();
    boolean delete(RequestOrderDetail requestOrderDetail);
    List<RequestOrderDetail> findByidReqOrderDocument(Integer idRequestOrderDocument);
}
