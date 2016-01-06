package mx.bidg.service;

import mx.bidg.model.CArticles;
import mx.bidg.model.CAttributes;
import mx.bidg.model.Properties;
import mx.bidg.model.Stocks;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 5/01/16.
 */
@Service
@Transactional
public interface PropertiesService {
    List<Properties> getAllFor(Stocks stock);
    Properties save(Properties property, CArticles article, CAttributes attribute);
    boolean delete(Properties property);
}
