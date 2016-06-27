/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.CProductTypes;
import mx.bidg.model.Providers;

import java.util.List;

/**
 *
 * @author sistemask
 */
public interface ProvidersDao extends InterfaceDao<Providers> {
    List<Providers> findByProductType(CProductTypes cProductTypes);
}
