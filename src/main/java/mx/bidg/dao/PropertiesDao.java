package mx.bidg.dao;

import mx.bidg.model.Properties;
import mx.bidg.model.Stocks;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 5/01/16.
 */
public interface PropertiesDao extends InterfaceDao<Properties> {
    List<Properties> getAllFor(Stocks stock);
}
