package mx.bidg.service;

import mx.bidg.model.ConciliationFile;

import java.util.List;

/**
 * Created by Desarrollador on 27/02/2017.
 */
public interface ConciliationFileService {
    ConciliationFile save(ConciliationFile conciliationFile);
    ConciliationFile update(ConciliationFile conciliationFile);
    ConciliationFile findById(Integer idConciliationFile);
    List<ConciliationFile> findAll();
    boolean delete(ConciliationFile conciliationFile);
}
