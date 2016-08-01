package mx.bidg.service;

import mx.bidg.model.DitributorsEmployeeDocuments;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
public interface DistributorsEmployeeDocumentsService {
    DitributorsEmployeeDocuments save (DitributorsEmployeeDocuments distributorsEmployeeDocuments);
    DitributorsEmployeeDocuments update(DitributorsEmployeeDocuments ditributorsEmployeeDocuments);
    DitributorsEmployeeDocuments findById(Integer idDitributorsEmployeeDocuments);
    List<DitributorsEmployeeDocuments> findAll ();
    boolean delete(DitributorsEmployeeDocuments ditributorsEmployeeDocuments);
    List<DitributorsEmployeeDocuments> findByDistributor(Integer idDistributor);
}
