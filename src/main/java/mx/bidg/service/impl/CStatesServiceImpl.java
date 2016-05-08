package mx.bidg.service.impl;

import mx.bidg.dao.CStatesDao;
import mx.bidg.model.CStates;
import mx.bidg.service.CStatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 7/05/16.
 */
@Service
@Transactional
public class CStatesServiceImpl implements CStatesService {

    @Autowired
     private CStatesDao dao;

    @Override
    public List<CStates> findAll() {
        return dao.findAll();
    }

}
