package mx.bidg.dao;

import mx.bidg.model.DitributorsEmployeeDocuments;

import java.util.List;

/**
 * Created by josueolvera on 27/07/16.
 */
public interface DistributorsEmployeeDocumentsDao extends InterfaceDao<DitributorsEmployeeDocuments> {

    List<DitributorsEmployeeDocuments> findByDistributor(Integer idDistributor);
}
