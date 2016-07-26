package mx.bidg.service;

import mx.bidg.model.Refunds;
import mx.bidg.model.Users;

import java.io.IOException;

/**
 * Created by gerardo8 on 25/07/16.
 */
public interface RefundsService  {
    Refunds save(String data, Users user) throws IOException;
    Refunds findById(Integer idRefund);
}
