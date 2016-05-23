package mx.bidg.service;

import mx.bidg.model.CPriority;

import java.util.List;

/**
 * Created by gerardo8 on 19/05/16.
 */
public interface CPriorityService {

    CPriority findById(int id);
    List<CPriority> findAll();
}
