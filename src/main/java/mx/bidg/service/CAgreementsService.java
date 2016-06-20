package mx.bidg.service;

import mx.bidg.model.CAgreements;

import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
public interface CAgreementsService {
    CAgreements save (CAgreements cAgreements);
    CAgreements update (CAgreements cAgreements);
    CAgreements findById (Integer idAgreement);
    List<CAgreements> findAll ();
    boolean delete (CAgreements cAgreements);
    boolean diferentAgreement(String agreementName);
}
