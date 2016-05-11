/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.Providers;

/**
 *
 * @author sistemask
 */
public interface ProvidersService {
    List<Providers> findAll();
    Providers findById(Integer idProvider);
    Providers save(Providers providers);
    Providers update(Providers providers);
    Boolean delete(Providers providers);
    void low(Integer idProviders);
}
