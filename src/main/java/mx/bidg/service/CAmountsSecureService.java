package mx.bidg.service;

import mx.bidg.model.CAmountsSecure;

import java.util.List;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
public interface CAmountsSecureService {
    List<CAmountsSecure>findAll();
    CAmountsSecure findById(Integer id);
}
