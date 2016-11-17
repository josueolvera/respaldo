package mx.bidg.service.impl;

import java.io.IOException;
import mx.bidg.dao.CommissionMultilevelDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.model.CommissionMultilevel;
import mx.bidg.service.CommissionMultilevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import mx.bidg.model.Users;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by PC_YAIR on 17/11/2016.
 */
@Service
@Transactional
public class CommissionMultilevelServiceImpl implements CommissionMultilevelService {

    @Autowired
    CommissionMultilevelDao commissionMultilevelDao;

    @Override
    public CommissionMultilevel findById(Integer idCommissionMultilevel) {
        return commissionMultilevelDao.findById(idCommissionMultilevel);
    }

    @Override
    public List<CommissionMultilevel> findAll() {
        return commissionMultilevelDao.findAll();
    }

    @Override
    public List<CommissionMultilevel> update(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CommissionMultilevel> save(MultipartFile file, String calculateDate, Users user) throws IOException, InvalidFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean existsMultilevelRecord(MultipartFile file, String calculateDate) throws IOException, InvalidFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
