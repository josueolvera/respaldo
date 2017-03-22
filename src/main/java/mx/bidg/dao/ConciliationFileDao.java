package mx.bidg.dao;

import mx.bidg.model.ConciliationFile;

/**
 * Created by Desarrollador on 27/02/2017.
 */
public interface ConciliationFileDao extends InterfaceDao<ConciliationFile> {
    ConciliationFile findByFileName(String fileName);
}
