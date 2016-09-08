package mx.bidg.service.impl;

import mx.bidg.dao.GroupAgreementsGoalPercentageDao;
import mx.bidg.model.GroupAgreementsGoalPercentage;
import mx.bidg.service.GroupAgreementsGoalPercentageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by josueolvera on 31/08/16.
 */
@Service
@Transactional
public class GroupAgreementsGoalPercentageServiceImpl implements GroupAgreementsGoalPercentageService {

    @Autowired
    GroupAgreementsGoalPercentageDao groupAgreementsGoalPercentageDao;

    @Override
    public GroupAgreementsGoalPercentage save(GroupAgreementsGoalPercentage groupAgreementsGoalPercentage) {
        return groupAgreementsGoalPercentageDao.save(groupAgreementsGoalPercentage);
    }

    @Override
    public GroupAgreementsGoalPercentage update(GroupAgreementsGoalPercentage groupAgreementsGoalPercentage) {
        return groupAgreementsGoalPercentageDao.update(groupAgreementsGoalPercentage);
    }

    @Override
    public GroupAgreementsGoalPercentage findById(Integer idGroupAgreementsGoalPercentage) {
        return groupAgreementsGoalPercentageDao.findById(idGroupAgreementsGoalPercentage);
    }

    @Override
    public boolean delete(GroupAgreementsGoalPercentage groupAgreementsGoalPercentage) {
        groupAgreementsGoalPercentageDao.delete(groupAgreementsGoalPercentage);
        return true;
    }

    @Override
    public List<GroupAgreementsGoalPercentage> findAll() {
        return groupAgreementsGoalPercentageDao.findAll();
    }
}
