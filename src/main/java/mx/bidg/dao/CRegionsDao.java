package mx.bidg.dao;

import mx.bidg.model.CRegions;

import java.util.List;

/**
 *
 * @author rubens
 */
public interface CRegionsDao extends InterfaceDao<CRegions> {
    List<CRegions> findRegionsBySaemFlag(Integer idRegion,Integer saemFlag);
    
}
