package mx.bidg.service;

import mx.bidg.model.GroupApportionment;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface GroupApportionmentService {
    GroupApportionment save(GroupApportionment groupApportionment);
    GroupApportionment update(GroupApportionment groupApportionment);
    GroupApportionment findById(Integer idGroupApportionment);
    List<GroupApportionment> findAll();
    boolean delete (GroupApportionment groupApportionment);
}
