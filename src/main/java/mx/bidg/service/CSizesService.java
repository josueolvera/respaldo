package mx.bidg.service;

import mx.bidg.model.CSizes;

import java.util.List;

/**
 * Created by gerardo8 on 01/08/16.
 */
public interface CSizesService {
    List<CSizes> findAll();
    CSizes findById(Integer idSize);
    CSizes save(CSizes size);
    CSizes update(CSizes size);
    Boolean delete(CSizes size);
}
