package mx.bidg.service;

import mx.bidg.model.InsurancePremium;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
public interface InsurancePremiumService {
    List<InsurancePremium>findAll();
    InsurancePremium findById(Integer id);
    InsurancePremium save(InsurancePremium insurancePremium);
    InsurancePremium update(InsurancePremium insurancePremium);
    boolean delete(InsurancePremium insurancePremium);
    InsurancePremium findByTypeSecureAndAmountSecure (Integer idTypeSecure, Integer idAmountsSecure);
}
