package mx.bidg.dao;

import mx.bidg.model.CAgreements;

/**
 * Created by jolvera on 16/06/16.
 */
public interface CAgreementsDao extends InterfaceDao <CAgreements> {
    CAgreements findByName (String name);
}
