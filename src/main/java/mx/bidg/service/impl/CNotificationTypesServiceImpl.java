package mx.bidg.service.impl;

import mx.bidg.dao.CNotificationTypesDao;
import mx.bidg.model.CNotificationTypes;
import mx.bidg.service.CNotificationTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 11/03/16.
 */
@Service
@Transactional
public class CNotificationTypesServiceImpl implements CNotificationTypesService {

    @Autowired
    private CNotificationTypesDao notificationTypesDao;

    @Override
    public List<CNotificationTypes> findAll() {
        return notificationTypesDao.findAll();
    }
}
