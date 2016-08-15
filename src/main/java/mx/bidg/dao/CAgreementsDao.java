package mx.bidg.dao;

import mx.bidg.model.CAgreements;

import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
public interface CAgreementsDao extends InterfaceDao <CAgreements> {
    CAgreements findByName (String name);
    List<CAgreements> findAgreementsActives();
}
