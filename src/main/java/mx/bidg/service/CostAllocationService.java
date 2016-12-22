package mx.bidg.service;

import mx.bidg.model.CostAllocation;

import java.util.List;

/**
 * Created by Kevin Salvador on 20/12/2016.
 */
public interface CostAllocationService {
    CostAllocation save(CostAllocation costAllocation);
    CostAllocation update(CostAllocation costAllocation);
    CostAllocation findById(Integer idCA);
    List<CostAllocation>findAll();
    boolean delete(CostAllocation costAllocation);
}
