package mx.bidg.dao;

import mx.bidg.model.Refunds;

import java.util.List;

/**
 * Created by gerardo8 on 25/07/16.
 */
public interface RefundsDao extends InterfaceDao<Refunds> {
    List<Refunds> getRefunds(Integer idUser);
}
