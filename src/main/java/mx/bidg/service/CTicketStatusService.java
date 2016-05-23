package mx.bidg.service;

import mx.bidg.model.CTicketStatus;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
public interface CTicketStatusService {

    CTicketStatus findById(int id);
    List<CTicketStatus> findAll();
}
