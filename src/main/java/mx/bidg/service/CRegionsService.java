package mx.bidg.service;

import java.util.List;
import mx.bidg.model.CRegions;

/**
 *
 * @author rubens
 */
public interface CRegionsService {
    List<CRegions> findAll();
    List<CRegions> findBySaemFlags(Integer idRegion, Integer saemFlag);
    
}
