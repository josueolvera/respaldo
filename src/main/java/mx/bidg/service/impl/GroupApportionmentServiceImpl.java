package mx.bidg.service.impl;

import mx.bidg.dao.GroupApportionmentDao;
import mx.bidg.model.GroupApportionment;
import mx.bidg.service.GroupApportionmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
@Service
@Transactional
public class GroupApportionmentServiceImpl implements GroupApportionmentService {

    @Autowired
    GroupApportionmentDao groupApportionmentDao;

    @Override
    public GroupApportionment save(GroupApportionment groupApportionment) {
        return groupApportionmentDao.save(groupApportionment);
    }

    @Override
    public GroupApportionment update(GroupApportionment groupApportionment) {
        return groupApportionmentDao.update(groupApportionment);
    }

    @Override
    public GroupApportionment findById(Integer idGroupApportionment) {
        return groupApportionmentDao.findById(idGroupApportionment);
    }

    @Override
    public List<GroupApportionment> findAll() {
        return groupApportionmentDao.findAll();
    }

    @Override
    public boolean delete(GroupApportionment groupApportionment) {
        groupApportionmentDao.delete(groupApportionment);
        return true;
    }
}
