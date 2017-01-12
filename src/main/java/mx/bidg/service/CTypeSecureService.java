package mx.bidg.service;

import mx.bidg.model.CTypeSecure;

import java.util.List;

/**
 * Created by Desarrollador on 12/01/2017.
 */
public interface CTypeSecureService {
    CTypeSecure save(CTypeSecure cTypeSecure);
    CTypeSecure update(CTypeSecure cTypeSecure);
    CTypeSecure findById(Integer idTypeSecure);
    List<CTypeSecure> findAll();
    boolean delete(CTypeSecure cTypeSecure);
}
