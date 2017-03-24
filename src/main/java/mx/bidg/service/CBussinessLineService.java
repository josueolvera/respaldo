package mx.bidg.service;

import mx.bidg.model.CBussinessLine;

import java.util.List;

/**
 * Created by Kevin Salvador on 21/03/2017.
 */
public interface CBussinessLineService {

    List<CBussinessLine>findAll();
    CBussinessLine findById(Integer idBusinessLine);
    CBussinessLine save(CBussinessLine cBussinessLine);
    CBussinessLine update(CBussinessLine cBussinessLine);
    boolean delete(CBussinessLine cBussinessLine);
}
