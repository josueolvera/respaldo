/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.util.List;
import mx.bidg.dao.CSqlDictionaryDao;
import mx.bidg.model.CSqlDictionary;
import mx.bidg.service.CSqlDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CSqlDictionaryServiceImpl implements CSqlDictionaryService {
    
    @Autowired
    private CSqlDictionaryDao cSqlDictionaryDao;
    
    @Override
    public List<CSqlDictionary> findAll() {
        return cSqlDictionaryDao.findAll();
    }
}
