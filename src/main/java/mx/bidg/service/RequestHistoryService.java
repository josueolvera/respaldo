package mx.bidg.service;

import mx.bidg.model.RequestHistory;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;

import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
public interface RequestHistoryService {
    RequestHistory save(RequestHistory requestHistory);
    RequestHistory findById(Integer idRequestHistory);
    RequestHistory update(RequestHistory requestHistory);
    List<RequestHistory>  findAll();
    boolean delete(RequestHistory requestHistory);
    RequestHistory saveRequest(Requests requests, Users user);
    List<RequestHistory> findAllForRequest(Integer idRequest);
}
