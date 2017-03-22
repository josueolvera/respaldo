package mx.bidg.dao;

import mx.bidg.model.CAmountsSecure;

import java.math.BigDecimal;

/**
 * Created by Kevin Salvador on 18/01/2017.
 */
public interface CAmountsSecureDao extends InterfaceDao<CAmountsSecure> {
    CAmountsSecure findByRode(BigDecimal rode);
}
