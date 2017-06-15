package mx.bidg.service.impl;

import mx.bidg.dao.CAccountBanksTypeDao;
import mx.bidg.model.CAccountBanksType;
import mx.bidg.service.CAccountBanksTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Leonardo on 12/06/2017.
 */
@Service
@Transactional
public class CAccountBanksTypeServiceImpl implements CAccountBanksTypeService{

    @Autowired
    private CAccountBanksTypeDao cAccountBanksTypeDao;

    @Override
    public List<CAccountBanksType> findAll(){
        return cAccountBanksTypeDao.findAll();
    }


    @Override
    public CAccountBanksType findById(Integer idAccountBankType) {return cAccountBanksTypeDao.findById(idAccountBankType);}

    @Override
    public CAccountBanksType save(CAccountBanksType cAccountBanksType) {return cAccountBanksTypeDao.save(cAccountBanksType);}

    @Override
    public CAccountBanksType update(CAccountBanksType cAccountBanksType) {return cAccountBanksTypeDao.update(cAccountBanksType);}

    @Override
    public boolean delete(CAccountBanksType cAccountBanksType) {return cAccountBanksTypeDao.delete(cAccountBanksType);}
}
