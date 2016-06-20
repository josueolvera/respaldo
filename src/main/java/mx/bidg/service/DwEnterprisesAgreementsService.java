package mx.bidg.service;


import mx.bidg.model.DwEnterprisesAgreements;

import java.util.List;

/**
 * Created by jolvera on 16/06/16.
 */
public interface DwEnterprisesAgreementsService {
    DwEnterprisesAgreements save (DwEnterprisesAgreements dwEnterprises);
    DwEnterprisesAgreements update (DwEnterprisesAgreements dwEnterprises);
    DwEnterprisesAgreements findById (Integer idDwEnterpriseAgreement);
    List<DwEnterprisesAgreements> findAll ();
    boolean delete (DwEnterprisesAgreements dwEnterprisesAgreements);

}
